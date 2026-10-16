package com.exemplo.pousada.controller;

import com.exemplo.pousada.model.Hospedagem;
import com.exemplo.pousada.repository.HospedagemRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospedagens")
public class HospedagemController {

    private final HospedagemRepository repository;

    public HospedagemController(HospedagemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Hospedagem> listar(@RequestParam(required = false) Long chaleId) {
        if (chaleId != null) {
            return repository.findByChaleId(chaleId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospedagem> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Hospedagem criar(@RequestBody Hospedagem hospedagem) {
        return repository.save(hospedagem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospedagem> atualizar(@PathVariable Long id, @RequestBody Hospedagem hospedagem) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        hospedagem.setId(id);
        return ResponseEntity.ok(repository.save(hospedagem));
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
