package com.exemplo.jardinagem.controller;

import com.exemplo.jardinagem.model.ServicoJardim;
import com.exemplo.jardinagem.repository.ServicoJardimRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos-jardim")
public class ServicoJardimController {

    private final ServicoJardimRepository repository;

    public ServicoJardimController(ServicoJardimRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ServicoJardim> listar(@RequestParam(required = false) Long clienteId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoJardim> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoJardim criar(@RequestBody ServicoJardim servicoJardim) {
        return repository.save(servicoJardim);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoJardim> atualizar(@PathVariable Long id, @RequestBody ServicoJardim servicoJardim) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servicoJardim.setId(id);
        return ResponseEntity.ok(repository.save(servicoJardim));
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
