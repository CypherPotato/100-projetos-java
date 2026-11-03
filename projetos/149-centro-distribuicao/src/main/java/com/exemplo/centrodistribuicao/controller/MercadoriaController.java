package com.exemplo.centrodistribuicao.controller;

import com.exemplo.centrodistribuicao.model.Mercadoria;
import com.exemplo.centrodistribuicao.service.MercadoriaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaController {

    private final MercadoriaService service;

    public MercadoriaController(MercadoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Mercadoria> listar(@RequestParam(required = false) Long armazemId) {
        return service.listar(armazemId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mercadoria criar(@RequestBody Mercadoria mercadoria) {
        return service.criar(mercadoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mercadoria> atualizar(@PathVariable Long id, @RequestBody Mercadoria mercadoria) {
        return service.atualizar(id, mercadoria)
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
