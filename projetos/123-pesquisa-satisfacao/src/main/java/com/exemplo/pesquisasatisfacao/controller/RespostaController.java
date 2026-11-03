package com.exemplo.pesquisasatisfacao.controller;

import com.exemplo.pesquisasatisfacao.model.Resposta;
import com.exemplo.pesquisasatisfacao.repository.RespostaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    private final RespostaRepository repository;

    public RespostaController(RespostaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Resposta> listar(@RequestParam(required = false) Long pesquisaId) {
        if (pesquisaId != null) {
            return repository.findByPesquisaId(pesquisaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resposta> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resposta criar(@RequestBody Resposta resposta) {
        return repository.save(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resposta> atualizar(@PathVariable Long id, @RequestBody Resposta resposta) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        resposta.setId(id);
        return ResponseEntity.ok(repository.save(resposta));
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
