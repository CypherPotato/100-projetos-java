package com.exemplo.pesquisasatisfacao.controller;

import com.exemplo.pesquisasatisfacao.model.Pesquisa;
import com.exemplo.pesquisasatisfacao.repository.PesquisaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pesquisas")
public class PesquisaController {

    private final PesquisaRepository repository;

    public PesquisaController(PesquisaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pesquisa> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pesquisa> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pesquisa criar(@RequestBody Pesquisa pesquisa) {
        return repository.save(pesquisa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pesquisa> atualizar(@PathVariable Long id, @RequestBody Pesquisa pesquisa) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pesquisa.setId(id);
        return ResponseEntity.ok(repository.save(pesquisa));
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
