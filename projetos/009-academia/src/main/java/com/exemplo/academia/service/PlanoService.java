package com.exemplo.academia.service;

import com.exemplo.academia.model.Plano;
import com.exemplo.academia.repository.PlanoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PlanoService {

    private final PlanoRepository repository;

    public PlanoService(PlanoRepository repository) {
        this.repository = repository;
    }

    public List<Plano> listar() {
        return repository.findAll();
    }

    public Optional<Plano> buscar(Long id) {
        return repository.findById(id);
    }

    public Plano criar(Plano plano) {
        return repository.save(plano);
    }

    public Optional<Plano> atualizar(Long id, Plano plano) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        plano.setId(id);
        return Optional.of(repository.save(plano));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
