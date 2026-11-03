package com.exemplo.escolamusica.controller;

import com.exemplo.escolamusica.model.Instrumento;
import com.exemplo.escolamusica.repository.InstrumentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instrumentos")
public class InstrumentoController {

    private final InstrumentoRepository repository;

    public InstrumentoController(InstrumentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Instrumento> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrumento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Instrumento criar(@RequestBody Instrumento instrumento) {
        return repository.save(instrumento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instrumento> atualizar(@PathVariable Long id, @RequestBody Instrumento instrumento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        instrumento.setId(id);
        return ResponseEntity.ok(repository.save(instrumento));
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
