package com.exemplo.frotaagricola.controller;

import com.exemplo.frotaagricola.model.Maquina;
import com.exemplo.frotaagricola.repository.MaquinaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maquinas")
public class MaquinaController {

    private final MaquinaRepository repository;

    public MaquinaController(MaquinaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Maquina> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maquina> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maquina criar(@RequestBody Maquina maquina) {
        return repository.save(maquina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maquina> atualizar(@PathVariable Long id, @RequestBody Maquina maquina) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        maquina.setId(id);
        return ResponseEntity.ok(repository.save(maquina));
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
