package com.exemplo.transporteescolar.controller;

import com.exemplo.transporteescolar.model.Rota;
import com.exemplo.transporteescolar.repository.RotaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rotas")
public class RotaController {

    private final RotaRepository repository;

    public RotaController(RotaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Rota> listar(@RequestParam(required = false) Long veiculoId) {
        if (veiculoId != null) {
            return repository.findByVeiculoId(veiculoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rota> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rota criar(@RequestBody Rota rota) {
        return repository.save(rota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rota> atualizar(@PathVariable Long id, @RequestBody Rota rota) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rota.setId(id);
        return ResponseEntity.ok(repository.save(rota));
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
