package com.exemplo.cursosonline.controller;

import com.exemplo.cursosonline.model.Modulo;
import com.exemplo.cursosonline.repository.ModuloRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modulos")
public class ModuloController {

    private final ModuloRepository repository;

    public ModuloController(ModuloRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Modulo> listar(@RequestParam(required = false) Long cursoId) {
        if (cursoId != null) {
            return repository.findByCursoId(cursoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modulo> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Modulo criar(@RequestBody Modulo modulo) {
        return repository.save(modulo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modulo> atualizar(@PathVariable Long id, @RequestBody Modulo modulo) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        modulo.setId(id);
        return ResponseEntity.ok(repository.save(modulo));
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
