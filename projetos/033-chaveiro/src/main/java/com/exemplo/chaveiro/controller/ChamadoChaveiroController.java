package com.exemplo.chaveiro.controller;

import com.exemplo.chaveiro.model.ChamadoChaveiro;
import com.exemplo.chaveiro.service.ChamadoChaveiroService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chamados-chaveiro")
public class ChamadoChaveiroController {

    private final ChamadoChaveiroService service;

    public ChamadoChaveiroController(ChamadoChaveiroService service) {
        this.service = service;
    }

    @GetMapping
    public List<ChamadoChaveiro> listar(@RequestParam(required = false) Long clienteId) {
        return service.listar(clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoChaveiro> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoChaveiro criar(@RequestBody ChamadoChaveiro chamadoChaveiro) {
        return service.criar(chamadoChaveiro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoChaveiro> atualizar(@PathVariable Long id, @RequestBody ChamadoChaveiro chamadoChaveiro) {
        return service.atualizar(id, chamadoChaveiro)
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
