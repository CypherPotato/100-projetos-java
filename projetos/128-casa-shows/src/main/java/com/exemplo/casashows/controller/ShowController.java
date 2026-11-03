package com.exemplo.casashows.controller;

import com.exemplo.casashows.model.Show;
import com.exemplo.casashows.service.ShowService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService service;

    public ShowController(ShowService service) {
        this.service = service;
    }

    @GetMapping
    public List<Show> listar(@RequestParam(required = false) Long artistaId) {
        return service.listar(artistaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Show criar(@RequestBody Show show) {
        return service.criar(show);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Show> atualizar(@PathVariable Long id, @RequestBody Show show) {
        return service.atualizar(id, show)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
