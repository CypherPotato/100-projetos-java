package com.exemplo.catalogoapis.controller;

import com.exemplo.catalogoapis.model.Endpoint;
import com.exemplo.catalogoapis.service.EndpointService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endpoints")
public class EndpointController {

    private final EndpointService service;

    public EndpointController(EndpointService service) {
        this.service = service;
    }

    @GetMapping
    public List<Endpoint> listar(@RequestParam(required = false) Long apiId) {
        return service.listar(apiId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endpoint> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Endpoint criar(@RequestBody Endpoint endpoint) {
        return service.criar(endpoint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endpoint> atualizar(@PathVariable Long id, @RequestBody Endpoint endpoint) {
        return service.atualizar(id, endpoint)
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
