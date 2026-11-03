package com.exemplo.centrodistribuicao.controller;

import com.exemplo.centrodistribuicao.model.Armazem;
import com.exemplo.centrodistribuicao.service.ArmazemService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/armazens")
public class ArmazemController {

    private final ArmazemService service;

    public ArmazemController(ArmazemService service) {
        this.service = service;
    }

    @GetMapping
    public List<Armazem> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Armazem> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Armazem criar(@RequestBody Armazem armazem) {
        return service.criar(armazem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Armazem> atualizar(@PathVariable Long id, @RequestBody Armazem armazem) {
        return service.atualizar(id, armazem)
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
