package com.exemplo.creche.service;

import com.exemplo.creche.model.Crianca;
import com.exemplo.creche.repository.CriancaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CriancaService {

    private final CriancaRepository repository;

    public CriancaService(CriancaRepository repository) {
        this.repository = repository;
    }

    public List<Crianca> listar(Long responsavelId) {
        if (responsavelId != null) {
            return repository.findByResponsavelId(responsavelId);
        }
        return repository.findAll();
    }

    public Optional<Crianca> buscar(Long id) {
        return repository.findById(id);
    }

    public Crianca criar(Crianca crianca) {
        return repository.save(crianca);
    }

    public Optional<Crianca> atualizar(Long id, Crianca crianca) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        crianca.setId(id);
        return Optional.of(repository.save(crianca));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
