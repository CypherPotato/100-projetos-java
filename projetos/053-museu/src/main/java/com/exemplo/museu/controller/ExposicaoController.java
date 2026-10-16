package com.exemplo.museu.controller;

import com.exemplo.museu.model.Exposicao;
import com.exemplo.museu.service.ExposicaoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exposicoes")
public class ExposicaoController {

    private final ExposicaoService service;

    public ExposicaoController(ExposicaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Exposicao> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exposicao> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exposicao criar(@RequestBody Exposicao exposicao) {
        return service.criar(exposicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exposicao> atualizar(@PathVariable Long id, @RequestBody Exposicao exposicao) {
        return service.atualizar(id, exposicao)
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
