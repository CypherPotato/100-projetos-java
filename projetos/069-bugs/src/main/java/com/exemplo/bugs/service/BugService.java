package com.exemplo.bugs.service;

import com.exemplo.bugs.model.Bug;
import com.exemplo.bugs.repository.BugRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BugService {

    private final BugRepository repository;

    public BugService(BugRepository repository) {
        this.repository = repository;
    }

    public List<Bug> listar(Long projetoId) {
        if (projetoId != null) {
            return repository.findByProjetoId(projetoId);
        }
        return repository.findAll();
    }

    public Optional<Bug> buscar(Long id) {
        return repository.findById(id);
    }

    public Bug criar(Bug bug) {
        return repository.save(bug);
    }

    public Optional<Bug> atualizar(Long id, Bug bug) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        bug.setId(id);
        return Optional.of(repository.save(bug));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
