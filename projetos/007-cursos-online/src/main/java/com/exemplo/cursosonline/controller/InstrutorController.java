package com.exemplo.cursosonline.controller;

import com.exemplo.cursosonline.model.Instrutor;
import com.exemplo.cursosonline.repository.InstrutorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    private final InstrutorRepository repository;

    public InstrutorController(InstrutorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Instrutor> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrutor> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Instrutor criar(@RequestBody Instrutor instrutor) {
        return repository.save(instrutor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instrutor> atualizar(@PathVariable Long id, @RequestBody Instrutor instrutor) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        instrutor.setId(id);
        return ResponseEntity.ok(repository.save(instrutor));
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
