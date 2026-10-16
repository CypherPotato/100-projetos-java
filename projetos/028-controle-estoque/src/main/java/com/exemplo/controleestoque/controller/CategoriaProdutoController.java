package com.exemplo.controleestoque.controller;

import com.exemplo.controleestoque.model.CategoriaProduto;
import com.exemplo.controleestoque.repository.CategoriaProdutoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias-produto")
public class CategoriaProdutoController {

    private final CategoriaProdutoRepository repository;

    public CategoriaProdutoController(CategoriaProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CategoriaProduto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProduto> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaProduto criar(@RequestBody CategoriaProduto categoriaProduto) {
        return repository.save(categoriaProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProduto> atualizar(@PathVariable Long id, @RequestBody CategoriaProduto categoriaProduto) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaProduto.setId(id);
        return ResponseEntity.ok(repository.save(categoriaProduto));
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
