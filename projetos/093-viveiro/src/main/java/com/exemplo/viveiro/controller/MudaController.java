package com.exemplo.viveiro.controller;

import com.exemplo.viveiro.model.Muda;
import com.exemplo.viveiro.service.MudaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mudas")
public class MudaController {

    private final MudaService service;

    public MudaController(MudaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Muda> listar(@RequestParam(required = false) Long especieId) {
        return service.listar(especieId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Muda> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Muda criar(@RequestBody Muda muda) {
        return service.criar(muda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Muda> atualizar(@PathVariable Long id, @RequestBody Muda muda) {
        return service.atualizar(id, muda)
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
