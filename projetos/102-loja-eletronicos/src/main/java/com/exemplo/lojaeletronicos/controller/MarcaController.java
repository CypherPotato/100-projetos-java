package com.exemplo.lojaeletronicos.controller;

import com.exemplo.lojaeletronicos.model.Marca;
import com.exemplo.lojaeletronicos.repository.MarcaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaRepository repository;

    public MarcaController(MarcaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Marca> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Marca criar(@RequestBody Marca marca) {
        return repository.save(marca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> atualizar(@PathVariable Long id, @RequestBody Marca marca) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        marca.setId(id);
        return ResponseEntity.ok(repository.save(marca));
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
