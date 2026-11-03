package com.exemplo.catalogoapis.service;

import com.exemplo.catalogoapis.model.Equipe;
import com.exemplo.catalogoapis.repository.EquipeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EquipeService {

    private final EquipeRepository repository;

    public EquipeService(EquipeRepository repository) {
        this.repository = repository;
    }

    public List<Equipe> listar() {
        return repository.findAll();
    }

    public Optional<Equipe> buscar(Long id) {
        return repository.findById(id);
    }

    public Equipe criar(Equipe equipe) {
        return repository.save(equipe);
    }

    public Optional<Equipe> atualizar(Long id, Equipe equipe) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        equipe.setId(id);
        return Optional.of(repository.save(equipe));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
