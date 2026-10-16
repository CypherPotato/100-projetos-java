package com.exemplo.frotaveiculos.controller;

import com.exemplo.frotaveiculos.model.Abastecimento;
import com.exemplo.frotaveiculos.repository.AbastecimentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abastecimentos")
public class AbastecimentoController {

    private final AbastecimentoRepository repository;

    public AbastecimentoController(AbastecimentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Abastecimento> listar(@RequestParam(required = false) Long veiculoId) {
        if (veiculoId != null) {
            return repository.findByVeiculoId(veiculoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Abastecimento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Abastecimento criar(@RequestBody Abastecimento abastecimento) {
        return repository.save(abastecimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Abastecimento> atualizar(@PathVariable Long id, @RequestBody Abastecimento abastecimento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        abastecimento.setId(id);
        return ResponseEntity.ok(repository.save(abastecimento));
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
