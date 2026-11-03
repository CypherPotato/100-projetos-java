package com.exemplo.casashows.service;

import com.exemplo.casashows.model.Ingresso;
import com.exemplo.casashows.repository.IngressoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class IngressoService {

    private final IngressoRepository repository;

    public IngressoService(IngressoRepository repository) {
        this.repository = repository;
    }

    public List<Ingresso> listar(Long showId) {
        if (showId != null) {
            return repository.findByShowId(showId);
        }
        return repository.findAll();
    }

    public Optional<Ingresso> buscar(Long id) {
        return repository.findById(id);
    }

    public Ingresso criar(Ingresso ingresso) {
        return repository.save(ingresso);
    }

    public Optional<Ingresso> atualizar(Long id, Ingresso ingresso) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        ingresso.setId(id);
        return Optional.of(repository.save(ingresso));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
