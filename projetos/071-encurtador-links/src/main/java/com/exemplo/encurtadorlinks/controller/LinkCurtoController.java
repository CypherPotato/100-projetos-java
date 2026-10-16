package com.exemplo.encurtadorlinks.controller;

import com.exemplo.encurtadorlinks.model.LinkCurto;
import com.exemplo.encurtadorlinks.repository.LinkCurtoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/links-curtos")
public class LinkCurtoController {

    private final LinkCurtoRepository repository;

    public LinkCurtoController(LinkCurtoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<LinkCurto> listar(@RequestParam(required = false) Long usuarioId) {
        if (usuarioId != null) {
            return repository.findByUsuarioId(usuarioId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinkCurto> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LinkCurto criar(@RequestBody LinkCurto linkCurto) {
        return repository.save(linkCurto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinkCurto> atualizar(@PathVariable Long id, @RequestBody LinkCurto linkCurto) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        linkCurto.setId(id);
        return ResponseEntity.ok(repository.save(linkCurto));
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
