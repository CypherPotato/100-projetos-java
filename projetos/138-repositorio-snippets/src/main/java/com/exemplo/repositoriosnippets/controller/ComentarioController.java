package com.exemplo.repositoriosnippets.controller;

import com.exemplo.repositoriosnippets.model.Comentario;
import com.exemplo.repositoriosnippets.repository.ComentarioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioRepository repository;

    public ComentarioController(ComentarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Comentario> listar(@RequestParam(required = false) Long snippetId) {
        if (snippetId != null) {
            return repository.findBySnippetId(snippetId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comentario criar(@RequestBody Comentario comentario) {
        return repository.save(comentario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comentario> atualizar(@PathVariable Long id, @RequestBody Comentario comentario) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        comentario.setId(id);
        return ResponseEntity.ok(repository.save(comentario));
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
