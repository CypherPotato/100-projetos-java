package com.exemplo.marmitaria.controller;

import com.exemplo.marmitaria.model.Marmita;
import com.exemplo.marmitaria.repository.MarmitaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marmitas")
public class MarmitaController {

    private final MarmitaRepository repository;

    public MarmitaController(MarmitaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Marmita> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marmita> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Marmita criar(@RequestBody Marmita marmita) {
        return repository.save(marmita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marmita> atualizar(@PathVariable Long id, @RequestBody Marmita marmita) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        marmita.setId(id);
        return ResponseEntity.ok(repository.save(marmita));
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
