package com.exemplo.joalheria.controller;

import com.exemplo.joalheria.model.Joia;
import com.exemplo.joalheria.service.JoiaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/joias")
public class JoiaController {

    private final JoiaService service;

    public JoiaController(JoiaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Joia> listar(@RequestParam(required = false) Long categoriaId) {
        return service.listar(categoriaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joia> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Joia criar(@RequestBody Joia joia) {
        return service.criar(joia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Joia> atualizar(@PathVariable Long id, @RequestBody Joia joia) {
        return service.atualizar(id, joia)
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
