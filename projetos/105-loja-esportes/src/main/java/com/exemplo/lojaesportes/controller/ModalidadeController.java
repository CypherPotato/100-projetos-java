package com.exemplo.lojaesportes.controller;

import com.exemplo.lojaesportes.model.Modalidade;
import com.exemplo.lojaesportes.repository.ModalidadeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modalidades")
public class ModalidadeController {

    private final ModalidadeRepository repository;

    public ModalidadeController(ModalidadeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Modalidade> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modalidade> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Modalidade criar(@RequestBody Modalidade modalidade) {
        return repository.save(modalidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modalidade> atualizar(@PathVariable Long id, @RequestBody Modalidade modalidade) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        modalidade.setId(id);
        return ResponseEntity.ok(repository.save(modalidade));
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
