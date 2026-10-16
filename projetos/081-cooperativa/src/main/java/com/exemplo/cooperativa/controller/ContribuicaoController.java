package com.exemplo.cooperativa.controller;

import com.exemplo.cooperativa.model.Contribuicao;
import com.exemplo.cooperativa.service.ContribuicaoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contribuicoes")
public class ContribuicaoController {

    private final ContribuicaoService service;

    public ContribuicaoController(ContribuicaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contribuicao> listar(@RequestParam(required = false) Long cooperadoId) {
        return service.listar(cooperadoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contribuicao> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contribuicao criar(@RequestBody Contribuicao contribuicao) {
        return service.criar(contribuicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contribuicao> atualizar(@PathVariable Long id, @RequestBody Contribuicao contribuicao) {
        return service.atualizar(id, contribuicao)
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
