package com.exemplo.creche.service;

import com.exemplo.creche.model.Responsavel;
import com.exemplo.creche.repository.ResponsavelRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ResponsavelService {

    private final ResponsavelRepository repository;

    public ResponsavelService(ResponsavelRepository repository) {
        this.repository = repository;
    }

    public List<Responsavel> listar() {
        return repository.findAll();
    }

    public Optional<Responsavel> buscar(Long id) {
        return repository.findById(id);
    }

    public Responsavel criar(Responsavel responsavel) {
        return repository.save(responsavel);
    }

    public Optional<Responsavel> atualizar(Long id, Responsavel responsavel) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        responsavel.setId(id);
        return Optional.of(repository.save(responsavel));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
