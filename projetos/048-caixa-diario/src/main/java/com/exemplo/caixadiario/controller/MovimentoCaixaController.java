package com.exemplo.caixadiario.controller;

import com.exemplo.caixadiario.model.MovimentoCaixa;
import com.exemplo.caixadiario.service.MovimentoCaixaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimentos-caixa")
public class MovimentoCaixaController {

    private final MovimentoCaixaService service;

    public MovimentoCaixaController(MovimentoCaixaService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovimentoCaixa> listar(@RequestParam(required = false) Long caixaId) {
        return service.listar(caixaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentoCaixa> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimentoCaixa criar(@RequestBody MovimentoCaixa movimentoCaixa) {
        return service.criar(movimentoCaixa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimentoCaixa> atualizar(@PathVariable Long id, @RequestBody MovimentoCaixa movimentoCaixa) {
        return service.atualizar(id, movimentoCaixa)
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
