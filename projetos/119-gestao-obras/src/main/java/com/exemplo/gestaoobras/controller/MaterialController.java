package com.exemplo.gestaoobras.controller;

import com.exemplo.gestaoobras.model.Material;
import com.exemplo.gestaoobras.service.MaterialService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping
    public List<Material> listar(@RequestParam(required = false) Long obraId) {
        return service.listar(obraId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Material criar(@RequestBody Material material) {
        return service.criar(material);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> atualizar(@PathVariable Long id, @RequestBody Material material) {
        return service.atualizar(id, material)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
