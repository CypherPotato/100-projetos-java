package com.exemplo.clubexadrez.controller;

import com.exemplo.clubexadrez.model.Torneio;
import com.exemplo.clubexadrez.repository.TorneioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/torneios")
public class TorneioController {

    private final TorneioRepository repository;

    public TorneioController(TorneioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Torneio> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torneio> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Torneio criar(@RequestBody Torneio torneio) {
        return repository.save(torneio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Torneio> atualizar(@PathVariable Long id, @RequestBody Torneio torneio) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        torneio.setId(id);
        return ResponseEntity.ok(repository.save(torneio));
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
