package com.exemplo.foodtruck.controller;

import com.exemplo.foodtruck.model.PontoVenda;
import com.exemplo.foodtruck.repository.PontoVendaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pontos-venda")
public class PontoVendaController {

    private final PontoVendaRepository repository;

    public PontoVendaController(PontoVendaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PontoVenda> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoVenda> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PontoVenda criar(@RequestBody PontoVenda pontoVenda) {
        return repository.save(pontoVenda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoVenda> atualizar(@PathVariable Long id, @RequestBody PontoVenda pontoVenda) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pontoVenda.setId(id);
        return ResponseEntity.ok(repository.save(pontoVenda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
