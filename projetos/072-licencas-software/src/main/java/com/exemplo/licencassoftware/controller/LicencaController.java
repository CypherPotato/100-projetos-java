package com.exemplo.licencassoftware.controller;

import com.exemplo.licencassoftware.model.Licenca;
import com.exemplo.licencassoftware.service.LicencaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/licencas")
public class LicencaController {

    private final LicencaService service;

    public LicencaController(LicencaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Licenca> listar(@RequestParam(required = false) Long softwareId) {
        return service.listar(softwareId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Licenca> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Licenca criar(@RequestBody Licenca licenca) {
        return service.criar(licenca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Licenca> atualizar(@PathVariable Long id, @RequestBody Licenca licenca) {
        return service.atualizar(id, licenca)
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
