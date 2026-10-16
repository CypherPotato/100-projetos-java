package com.exemplo.agendacontatos.controller;

import com.exemplo.agendacontatos.model.Contato;
import com.exemplo.agendacontatos.repository.ContatoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    private final ContatoRepository repository;

    public ContatoController(ContatoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Contato> listar(@RequestParam(required = false) Long grupoId) {
        if (grupoId != null) {
            return repository.findByGrupoId(grupoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contato criar(@RequestBody Contato contato) {
        return repository.save(contato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizar(@PathVariable Long id, @RequestBody Contato contato) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contato.setId(id);
        return ResponseEntity.ok(repository.save(contato));
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
