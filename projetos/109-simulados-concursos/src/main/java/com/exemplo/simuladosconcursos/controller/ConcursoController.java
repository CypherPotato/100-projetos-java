package com.exemplo.simuladosconcursos.controller;

import com.exemplo.simuladosconcursos.model.Concurso;
import com.exemplo.simuladosconcursos.repository.ConcursoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/concursos")
public class ConcursoController {

    private final ConcursoRepository repository;

    public ConcursoController(ConcursoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Concurso> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concurso> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Concurso criar(@RequestBody Concurso concurso) {
        return repository.save(concurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Concurso> atualizar(@PathVariable Long id, @RequestBody Concurso concurso) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        concurso.setId(id);
        return ResponseEntity.ok(repository.save(concurso));
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
