package com.exemplo.catalogoapis.controller;

import com.exemplo.catalogoapis.model.Api;
import com.exemplo.catalogoapis.service.ApiService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apis")
public class ApiController {

    private final ApiService service;

    public ApiController(ApiService service) {
        this.service = service;
    }

    @GetMapping
    public List<Api> listar(@RequestParam(required = false) Long equipeId) {
        return service.listar(equipeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Api> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Api criar(@RequestBody Api api) {
        return service.criar(api);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Api> atualizar(@PathVariable Long id, @RequestBody Api api) {
        return service.atualizar(id, api)
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
