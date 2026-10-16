package com.exemplo.bugs.controller;

import com.exemplo.bugs.model.Bug;
import com.exemplo.bugs.service.BugService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bugs")
public class BugController {

    private final BugService service;

    public BugController(BugService service) {
        this.service = service;
    }

    @GetMapping
    public List<Bug> listar(@RequestParam(required = false) Long projetoId) {
        return service.listar(projetoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bug> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bug criar(@RequestBody Bug bug) {
        return service.criar(bug);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bug> atualizar(@PathVariable Long id, @RequestBody Bug bug) {
        return service.atualizar(id, bug)
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
