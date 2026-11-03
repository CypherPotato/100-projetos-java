package com.exemplo.clubexadrez.controller;

import com.exemplo.clubexadrez.model.Partida;
import com.exemplo.clubexadrez.repository.PartidaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaRepository repository;

    public PartidaController(PartidaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Partida> listar(@RequestParam(required = false) Long brancasId, @RequestParam(required = false) Long torneioId) {
        if (brancasId != null) {
            return repository.findByBrancasId(brancasId);
        }
        if (torneioId != null) {
            return repository.findByTorneioId(torneioId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Partida criar(@RequestBody Partida partida) {
        return repository.save(partida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partida> atualizar(@PathVariable Long id, @RequestBody Partida partida) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        partida.setId(id);
        return ResponseEntity.ok(repository.save(partida));
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
