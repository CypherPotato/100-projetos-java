package com.exemplo.empresamudancas.controller;

import com.exemplo.empresamudancas.model.Caminhao;
import com.exemplo.empresamudancas.repository.CaminhaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caminhoes")
public class CaminhaoController {

    private final CaminhaoRepository repository;

    public CaminhaoController(CaminhaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Caminhao> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caminhao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Caminhao criar(@RequestBody Caminhao caminhao) {
        return repository.save(caminhao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caminhao> atualizar(@PathVariable Long id, @RequestBody Caminhao caminhao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        caminhao.setId(id);
        return ResponseEntity.ok(repository.save(caminhao));
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
