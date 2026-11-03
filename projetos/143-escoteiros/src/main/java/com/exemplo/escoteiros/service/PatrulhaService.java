package com.exemplo.escoteiros.service;

import com.exemplo.escoteiros.model.Patrulha;
import com.exemplo.escoteiros.repository.PatrulhaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PatrulhaService {

    private final PatrulhaRepository repository;

    public PatrulhaService(PatrulhaRepository repository) {
        this.repository = repository;
    }

    public List<Patrulha> listar() {
        return repository.findAll();
    }

    public Optional<Patrulha> buscar(Long id) {
        return repository.findById(id);
    }

    public Patrulha criar(Patrulha patrulha) {
        return repository.save(patrulha);
    }

    public Optional<Patrulha> atualizar(Long id, Patrulha patrulha) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        patrulha.setId(id);
        return Optional.of(repository.save(patrulha));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
