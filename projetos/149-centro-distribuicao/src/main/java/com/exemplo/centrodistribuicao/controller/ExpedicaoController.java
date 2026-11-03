package com.exemplo.centrodistribuicao.controller;

import com.exemplo.centrodistribuicao.model.Expedicao;
import com.exemplo.centrodistribuicao.service.ExpedicaoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expedicoes")
public class ExpedicaoController {

    private final ExpedicaoService service;

    public ExpedicaoController(ExpedicaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Expedicao> listar(@RequestParam(required = false) Long mercadoriaId) {
        return service.listar(mercadoriaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expedicao> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expedicao criar(@RequestBody Expedicao expedicao) {
        return service.criar(expedicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expedicao> atualizar(@PathVariable Long id, @RequestBody Expedicao expedicao) {
        return service.atualizar(id, expedicao)
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
