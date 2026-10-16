package com.exemplo.servidores.controller;

import com.exemplo.servidores.model.Servidor;
import com.exemplo.servidores.repository.ServidorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servidores")
public class ServidorController {

    private final ServidorRepository repository;

    public ServidorController(ServidorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Servidor> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servidor> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Servidor criar(@RequestBody Servidor servidor) {
        return repository.save(servidor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servidor> atualizar(@PathVariable Long id, @RequestBody Servidor servidor) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servidor.setId(id);
        return ResponseEntity.ok(repository.save(servidor));
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
