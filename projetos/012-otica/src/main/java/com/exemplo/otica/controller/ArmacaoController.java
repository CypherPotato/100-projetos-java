package com.exemplo.otica.controller;

import com.exemplo.otica.model.Armacao;
import com.exemplo.otica.service.ArmacaoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/armacoes")
public class ArmacaoController {

    private final ArmacaoService service;

    public ArmacaoController(ArmacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Armacao> listar(@RequestParam(required = false) Long marcaId) {
        return service.listar(marcaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Armacao> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Armacao criar(@RequestBody Armacao armacao) {
        return service.criar(armacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Armacao> atualizar(@PathVariable Long id, @RequestBody Armacao armacao) {
        return service.atualizar(id, armacao)
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
