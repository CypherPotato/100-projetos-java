package com.exemplo.transporteescolar.controller;

import com.exemplo.transporteescolar.model.Passageiro;
import com.exemplo.transporteescolar.repository.PassageiroRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passageiros")
public class PassageiroController {

    private final PassageiroRepository repository;

    public PassageiroController(PassageiroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Passageiro> listar(@RequestParam(required = false) Long rotaId) {
        if (rotaId != null) {
            return repository.findByRotaId(rotaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passageiro> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Passageiro criar(@RequestBody Passageiro passageiro) {
        return repository.save(passageiro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passageiro> atualizar(@PathVariable Long id, @RequestBody Passageiro passageiro) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        passageiro.setId(id);
        return ResponseEntity.ok(repository.save(passageiro));
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
