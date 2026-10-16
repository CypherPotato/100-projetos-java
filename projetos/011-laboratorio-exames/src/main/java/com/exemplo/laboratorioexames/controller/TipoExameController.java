package com.exemplo.laboratorioexames.controller;

import com.exemplo.laboratorioexames.model.TipoExame;
import com.exemplo.laboratorioexames.repository.TipoExameRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipos-exame")
public class TipoExameController {

    private final TipoExameRepository repository;

    public TipoExameController(TipoExameRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TipoExame> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoExame> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoExame criar(@RequestBody TipoExame tipoExame) {
        return repository.save(tipoExame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoExame> atualizar(@PathVariable Long id, @RequestBody TipoExame tipoExame) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tipoExame.setId(id);
        return ResponseEntity.ok(repository.save(tipoExame));
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
