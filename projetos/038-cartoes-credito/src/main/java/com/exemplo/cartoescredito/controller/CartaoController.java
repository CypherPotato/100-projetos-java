package com.exemplo.cartoescredito.controller;

import com.exemplo.cartoescredito.model.Cartao;
import com.exemplo.cartoescredito.repository.CartaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoRepository repository;

    public CartaoController(CartaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cartao> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cartao criar(@RequestBody Cartao cartao) {
        return repository.save(cartao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cartao> atualizar(@PathVariable Long id, @RequestBody Cartao cartao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartao.setId(id);
        return ResponseEntity.ok(repository.save(cartao));
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
