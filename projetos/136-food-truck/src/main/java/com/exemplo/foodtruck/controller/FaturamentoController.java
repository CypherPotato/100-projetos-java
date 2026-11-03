package com.exemplo.foodtruck.controller;

import com.exemplo.foodtruck.model.Faturamento;
import com.exemplo.foodtruck.repository.FaturamentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faturamentos")
public class FaturamentoController {

    private final FaturamentoRepository repository;

    public FaturamentoController(FaturamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Faturamento> listar(@RequestParam(required = false) Long pontoVendaId) {
        if (pontoVendaId != null) {
            return repository.findByPontoVendaId(pontoVendaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faturamento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Faturamento criar(@RequestBody Faturamento faturamento) {
        return repository.save(faturamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faturamento> atualizar(@PathVariable Long id, @RequestBody Faturamento faturamento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        faturamento.setId(id);
        return ResponseEntity.ok(repository.save(faturamento));
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
