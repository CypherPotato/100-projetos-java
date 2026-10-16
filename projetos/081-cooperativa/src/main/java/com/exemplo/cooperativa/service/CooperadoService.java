package com.exemplo.cooperativa.service;

import com.exemplo.cooperativa.model.Cooperado;
import com.exemplo.cooperativa.repository.CooperadoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CooperadoService {

    private final CooperadoRepository repository;

    public CooperadoService(CooperadoRepository repository) {
        this.repository = repository;
    }

    public List<Cooperado> listar() {
        return repository.findAll();
    }

    public Optional<Cooperado> buscar(Long id) {
        return repository.findById(id);
    }

    public Cooperado criar(Cooperado cooperado) {
        return repository.save(cooperado);
    }

    public Optional<Cooperado> atualizar(Long id, Cooperado cooperado) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        cooperado.setId(id);
        return Optional.of(repository.save(cooperado));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
