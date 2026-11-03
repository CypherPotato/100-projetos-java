package com.exemplo.controleassinaturas.controller;

import com.exemplo.controleassinaturas.model.Pagamento;
import com.exemplo.controleassinaturas.repository.PagamentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoRepository repository;

    public PagamentoController(PagamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pagamento> listar(@RequestParam(required = false) Long assinaturaId) {
        if (assinaturaId != null) {
            return repository.findByAssinaturaId(assinaturaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pagamento criar(@RequestBody Pagamento pagamento) {
        return repository.save(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizar(@PathVariable Long id, @RequestBody Pagamento pagamento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pagamento.setId(id);
        return ResponseEntity.ok(repository.save(pagamento));
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
