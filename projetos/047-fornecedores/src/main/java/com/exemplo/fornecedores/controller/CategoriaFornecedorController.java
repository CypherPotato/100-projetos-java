package com.exemplo.fornecedores.controller;

import com.exemplo.fornecedores.model.CategoriaFornecedor;
import com.exemplo.fornecedores.repository.CategoriaFornecedorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias-fornecedor")
public class CategoriaFornecedorController {

    private final CategoriaFornecedorRepository repository;

    public CategoriaFornecedorController(CategoriaFornecedorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CategoriaFornecedor> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaFornecedor> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaFornecedor criar(@RequestBody CategoriaFornecedor categoriaFornecedor) {
        return repository.save(categoriaFornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaFornecedor> atualizar(@PathVariable Long id, @RequestBody CategoriaFornecedor categoriaFornecedor) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaFornecedor.setId(id);
        return ResponseEntity.ok(repository.save(categoriaFornecedor));
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
