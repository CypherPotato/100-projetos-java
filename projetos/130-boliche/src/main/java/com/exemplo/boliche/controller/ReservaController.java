package com.exemplo.boliche.controller;

import com.exemplo.boliche.model.Reserva;
import com.exemplo.boliche.repository.ReservaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaRepository repository;

    public ReservaController(ReservaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Reserva> listar(@RequestParam(required = false) Long pistaId) {
        if (pistaId != null) {
            return repository.findByPistaId(pistaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva criar(@RequestBody Reserva reserva) {
        return repository.save(reserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reserva.setId(id);
        return ResponseEntity.ok(repository.save(reserva));
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
