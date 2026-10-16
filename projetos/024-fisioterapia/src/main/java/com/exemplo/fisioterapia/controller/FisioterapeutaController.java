package com.exemplo.fisioterapia.controller;

import com.exemplo.fisioterapia.model.Fisioterapeuta;
import com.exemplo.fisioterapia.service.FisioterapeutaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fisioterapeutas")
public class FisioterapeutaController {

    private final FisioterapeutaService service;

    public FisioterapeutaController(FisioterapeutaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Fisioterapeuta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fisioterapeuta> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fisioterapeuta criar(@RequestBody Fisioterapeuta fisioterapeuta) {
        return service.criar(fisioterapeuta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fisioterapeuta> atualizar(@PathVariable Long id, @RequestBody Fisioterapeuta fisioterapeuta) {
        return service.atualizar(id, fisioterapeuta)
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
