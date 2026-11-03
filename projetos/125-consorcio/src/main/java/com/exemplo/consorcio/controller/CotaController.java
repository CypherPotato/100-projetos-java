package com.exemplo.consorcio.controller;

import com.exemplo.consorcio.model.Cota;
import com.exemplo.consorcio.service.CotaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cotas")
public class CotaController {

    private final CotaService service;

    public CotaController(CotaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cota> listar(@RequestParam(required = false) Long grupoId) {
        return service.listar(grupoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cota> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cota criar(@RequestBody Cota cota) {
        return service.criar(cota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cota> atualizar(@PathVariable Long id, @RequestBody Cota cota) {
        return service.atualizar(id, cota)
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
