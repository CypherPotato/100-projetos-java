package com.exemplo.farmacia.controller;

import com.exemplo.farmacia.model.Medicamento;
import com.exemplo.farmacia.repository.MedicamentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoRepository repository;

    public MedicamentoController(MedicamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Medicamento> listar(@RequestParam(required = false) Long fabricanteId) {
        if (fabricanteId != null) {
            return repository.findByFabricanteId(fabricanteId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medicamento criar(@RequestBody Medicamento medicamento) {
        return repository.save(medicamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> atualizar(@PathVariable Long id, @RequestBody Medicamento medicamento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicamento.setId(id);
        return ResponseEntity.ok(repository.save(medicamento));
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
