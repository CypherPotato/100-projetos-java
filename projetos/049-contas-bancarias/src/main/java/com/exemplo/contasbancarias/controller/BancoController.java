package com.exemplo.contasbancarias.controller;

import com.exemplo.contasbancarias.model.Banco;
import com.exemplo.contasbancarias.repository.BancoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bancos")
public class BancoController {

    private final BancoRepository repository;

    public BancoController(BancoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Banco> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banco> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Banco criar(@RequestBody Banco banco) {
        return repository.save(banco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banco> atualizar(@PathVariable Long id, @RequestBody Banco banco) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        banco.setId(id);
        return ResponseEntity.ok(repository.save(banco));
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
