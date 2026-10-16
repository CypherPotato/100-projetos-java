package com.exemplo.padaria.service;

import com.exemplo.padaria.model.Fornada;
import com.exemplo.padaria.repository.FornadaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FornadaService {

    private final FornadaRepository repository;

    public FornadaService(FornadaRepository repository) {
        this.repository = repository;
    }

    public List<Fornada> listar(Long padeiroId) {
        if (padeiroId != null) {
            return repository.findByPadeiroId(padeiroId);
        }
        return repository.findAll();
    }

    public Optional<Fornada> buscar(Long id) {
        return repository.findById(id);
    }

    public Fornada criar(Fornada fornada) {
        return repository.save(fornada);
    }

    public Optional<Fornada> atualizar(Long id, Fornada fornada) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        fornada.setId(id);
        return Optional.of(repository.save(fornada));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
