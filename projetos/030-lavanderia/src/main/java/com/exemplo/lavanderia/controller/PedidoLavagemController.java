package com.exemplo.lavanderia.controller;

import com.exemplo.lavanderia.model.PedidoLavagem;
import com.exemplo.lavanderia.service.PedidoLavagemService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos-lavagem")
public class PedidoLavagemController {

    private final PedidoLavagemService service;

    public PedidoLavagemController(PedidoLavagemService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoLavagem> listar(@RequestParam(required = false) Long clienteId) {
        return service.listar(clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoLavagem> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoLavagem criar(@RequestBody PedidoLavagem pedidoLavagem) {
        return service.criar(pedidoLavagem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoLavagem> atualizar(@PathVariable Long id, @RequestBody PedidoLavagem pedidoLavagem) {
        return service.atualizar(id, pedidoLavagem)
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
