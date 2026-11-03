package com.exemplo.clubexadrez.controller;

import com.exemplo.clubexadrez.model.Jogador;
import com.exemplo.clubexadrez.repository.JogadorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    private final JogadorRepository repository;

    public JogadorController(JogadorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Jogador> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogador> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Jogador criar(@RequestBody Jogador jogador) {
        return repository.save(jogador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogador> atualizar(@PathVariable Long id, @RequestBody Jogador jogador) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jogador.setId(id);
        return ResponseEntity.ok(repository.save(jogador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
