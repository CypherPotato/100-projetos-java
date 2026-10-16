package com.exemplo.igreja.controller;

import com.exemplo.igreja.model.Membro;
import com.exemplo.igreja.repository.MembroRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membros")
public class MembroController {

    private final MembroRepository repository;

    public MembroController(MembroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Membro> listar(@RequestParam(required = false) Long celulaId) {
        if (celulaId != null) {
            return repository.findByCelulaId(celulaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membro> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Membro criar(@RequestBody Membro membro) {
        return repository.save(membro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Membro> atualizar(@PathVariable Long id, @RequestBody Membro membro) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        membro.setId(id);
        return ResponseEntity.ok(repository.save(membro));
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
