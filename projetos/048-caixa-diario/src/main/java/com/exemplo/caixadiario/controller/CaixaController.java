package com.exemplo.caixadiario.controller;

import com.exemplo.caixadiario.model.Caixa;
import com.exemplo.caixadiario.service.CaixaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caixas")
public class CaixaController {

    private final CaixaService service;

    public CaixaController(CaixaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Caixa> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caixa> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Caixa criar(@RequestBody Caixa caixa) {
        return service.criar(caixa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caixa> atualizar(@PathVariable Long id, @RequestBody Caixa caixa) {
        return service.atualizar(id, caixa)
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
