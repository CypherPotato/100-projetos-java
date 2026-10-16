package com.exemplo.agenciaviagens.controller;

import com.exemplo.agenciaviagens.model.Pacote;
import com.exemplo.agenciaviagens.service.PacoteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacotes")
public class PacoteController {

    private final PacoteService service;

    public PacoteController(PacoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pacote> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pacote> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pacote criar(@RequestBody Pacote pacote) {
        return service.criar(pacote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pacote> atualizar(@PathVariable Long id, @RequestBody Pacote pacote) {
        return service.atualizar(id, pacote)
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
