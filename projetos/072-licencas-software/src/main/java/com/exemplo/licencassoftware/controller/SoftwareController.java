package com.exemplo.licencassoftware.controller;

import com.exemplo.licencassoftware.model.Software;
import com.exemplo.licencassoftware.service.SoftwareService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/softwares")
public class SoftwareController {

    private final SoftwareService service;

    public SoftwareController(SoftwareService service) {
        this.service = service;
    }

    @GetMapping
    public List<Software> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Software> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Software criar(@RequestBody Software software) {
        return service.criar(software);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Software> atualizar(@PathVariable Long id, @RequestBody Software software) {
        return service.atualizar(id, software)
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
