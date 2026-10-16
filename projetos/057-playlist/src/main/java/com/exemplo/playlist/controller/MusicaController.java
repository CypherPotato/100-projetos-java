package com.exemplo.playlist.controller;

import com.exemplo.playlist.model.Musica;
import com.exemplo.playlist.repository.MusicaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    private final MusicaRepository repository;

    public MusicaController(MusicaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Musica> listar(@RequestParam(required = false) Long artistaId) {
        if (artistaId != null) {
            return repository.findByArtistaId(artistaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Musica> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Musica criar(@RequestBody Musica musica) {
        return repository.save(musica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Musica> atualizar(@PathVariable Long id, @RequestBody Musica musica) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        musica.setId(id);
        return ResponseEntity.ok(repository.save(musica));
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
