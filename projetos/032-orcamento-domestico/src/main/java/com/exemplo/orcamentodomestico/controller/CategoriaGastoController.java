package com.exemplo.orcamentodomestico.controller;

import com.exemplo.orcamentodomestico.model.CategoriaGasto;
import com.exemplo.orcamentodomestico.repository.CategoriaGastoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias-gasto")
public class CategoriaGastoController {

    private final CategoriaGastoRepository repository;

    public CategoriaGastoController(CategoriaGastoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CategoriaGasto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaGasto> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaGasto criar(@RequestBody CategoriaGasto categoriaGasto) {
        return repository.save(categoriaGasto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaGasto> atualizar(@PathVariable Long id, @RequestBody CategoriaGasto categoriaGasto) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaGasto.setId(id);
        return ResponseEntity.ok(repository.save(categoriaGasto));
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
