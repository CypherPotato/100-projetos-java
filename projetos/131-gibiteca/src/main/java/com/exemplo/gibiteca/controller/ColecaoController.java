package com.exemplo.gibiteca.controller;

import com.exemplo.gibiteca.model.Colecao;
import com.exemplo.gibiteca.service.ColecaoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/colecoes")
public class ColecaoController {

    private final ColecaoService service;

    public ColecaoController(ColecaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Colecao> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colecao> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Colecao criar(@RequestBody Colecao colecao) {
        return service.criar(colecao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colecao> atualizar(@PathVariable Long id, @RequestBody Colecao colecao) {
        return service.atualizar(id, colecao)
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
