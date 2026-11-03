package com.exemplo.simuladosconcursos.controller;

import com.exemplo.simuladosconcursos.model.Simulado;
import com.exemplo.simuladosconcursos.repository.SimuladoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simulados")
public class SimuladoController {

    private final SimuladoRepository repository;

    public SimuladoController(SimuladoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Simulado> listar(@RequestParam(required = false) Long concursoId) {
        if (concursoId != null) {
            return repository.findByConcursoId(concursoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Simulado> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Simulado criar(@RequestBody Simulado simulado) {
        return repository.save(simulado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Simulado> atualizar(@PathVariable Long id, @RequestBody Simulado simulado) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        simulado.setId(id);
        return ResponseEntity.ok(repository.save(simulado));
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
