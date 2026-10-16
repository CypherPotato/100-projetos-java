package com.exemplo.forum.controller;

import com.exemplo.forum.model.Resposta;
import com.exemplo.forum.service.RespostaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    private final RespostaService service;

    public RespostaController(RespostaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Resposta> listar(@RequestParam(required = false) Long topicoId, @RequestParam(required = false) Long autorId) {
        return service.listar(topicoId, autorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resposta> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resposta criar(@RequestBody Resposta resposta) {
        return service.criar(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resposta> atualizar(@PathVariable Long id, @RequestBody Resposta resposta) {
        return service.atualizar(id, resposta)
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
