package com.exemplo.investimentos.controller;

import com.exemplo.investimentos.model.Carteira;
import com.exemplo.investimentos.repository.CarteiraRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    private final CarteiraRepository repository;

    public CarteiraController(CarteiraRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Carteira> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carteira> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Carteira criar(@RequestBody Carteira carteira) {
        return repository.save(carteira);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carteira> atualizar(@PathVariable Long id, @RequestBody Carteira carteira) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        carteira.setId(id);
        return ResponseEntity.ok(repository.save(carteira));
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
