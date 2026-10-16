package com.exemplo.ong.controller;

import com.exemplo.ong.model.Voluntario;
import com.exemplo.ong.repository.VoluntarioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voluntarios")
public class VoluntarioController {

    private final VoluntarioRepository repository;

    public VoluntarioController(VoluntarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Voluntario> listar(@RequestParam(required = false) Long acaoId) {
        if (acaoId != null) {
            return repository.findByAcaoId(acaoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voluntario> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Voluntario criar(@RequestBody Voluntario voluntario) {
        return repository.save(voluntario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voluntario> atualizar(@PathVariable Long id, @RequestBody Voluntario voluntario) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        voluntario.setId(id);
        return ResponseEntity.ok(repository.save(voluntario));
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
