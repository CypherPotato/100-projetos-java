package com.exemplo.agendacontatos.controller;

import com.exemplo.agendacontatos.model.Grupo;
import com.exemplo.agendacontatos.repository.GrupoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoRepository repository;

    public GrupoController(GrupoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Grupo> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupo> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Grupo criar(@RequestBody Grupo grupo) {
        return repository.save(grupo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupo> atualizar(@PathVariable Long id, @RequestBody Grupo grupo) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        grupo.setId(id);
        return ResponseEntity.ok(repository.save(grupo));
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
