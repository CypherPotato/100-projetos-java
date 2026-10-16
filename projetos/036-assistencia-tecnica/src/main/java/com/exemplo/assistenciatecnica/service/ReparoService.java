package com.exemplo.assistenciatecnica.service;

import com.exemplo.assistenciatecnica.model.Reparo;
import com.exemplo.assistenciatecnica.repository.ReparoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReparoService {

    private final ReparoRepository repository;

    public ReparoService(ReparoRepository repository) {
        this.repository = repository;
    }

    public List<Reparo> listar(Long aparelhoId) {
        if (aparelhoId != null) {
            return repository.findByAparelhoId(aparelhoId);
        }
        return repository.findAll();
    }

    public Optional<Reparo> buscar(Long id) {
        return repository.findById(id);
    }

    public Reparo criar(Reparo reparo) {
        return repository.save(reparo);
    }

    public Optional<Reparo> atualizar(Long id, Reparo reparo) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        reparo.setId(id);
        return Optional.of(repository.save(reparo));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
