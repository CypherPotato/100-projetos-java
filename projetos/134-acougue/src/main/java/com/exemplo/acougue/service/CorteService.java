package com.exemplo.acougue.service;

import com.exemplo.acougue.model.Corte;
import com.exemplo.acougue.repository.CorteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CorteService {

    private final CorteRepository repository;

    public CorteService(CorteRepository repository) {
        this.repository = repository;
    }

    public List<Corte> listar() {
        return repository.findAll();
    }

    public Optional<Corte> buscar(Long id) {
        return repository.findById(id);
    }

    public Corte criar(Corte corte) {
        return repository.save(corte);
    }

    public Optional<Corte> atualizar(Long id, Corte corte) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        corte.setId(id);
        return Optional.of(repository.save(corte));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
