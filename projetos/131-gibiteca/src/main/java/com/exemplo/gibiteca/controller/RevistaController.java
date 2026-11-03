package com.exemplo.gibiteca.controller;

import com.exemplo.gibiteca.model.Revista;
import com.exemplo.gibiteca.service.RevistaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revistas")
public class RevistaController {

    private final RevistaService service;

    public RevistaController(RevistaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Revista> listar(@RequestParam(required = false) Long colecaoId) {
        return service.listar(colecaoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revista> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Revista criar(@RequestBody Revista revista) {
        return service.criar(revista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Revista> atualizar(@PathVariable Long id, @RequestBody Revista revista) {
        return service.atualizar(id, revista)
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
