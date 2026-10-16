package com.exemplo.colecaofilmes.controller;

import com.exemplo.colecaofilmes.model.Diretor;
import com.exemplo.colecaofilmes.repository.DiretorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diretores")
public class DiretorController {

    private final DiretorRepository repository;

    public DiretorController(DiretorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Diretor> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diretor> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Diretor criar(@RequestBody Diretor diretor) {
        return repository.save(diretor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Diretor> atualizar(@PathVariable Long id, @RequestBody Diretor diretor) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        diretor.setId(id);
        return ResponseEntity.ok(repository.save(diretor));
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
