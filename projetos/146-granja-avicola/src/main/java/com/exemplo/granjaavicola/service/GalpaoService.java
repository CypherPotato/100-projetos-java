package com.exemplo.granjaavicola.service;

import com.exemplo.granjaavicola.model.Galpao;
import com.exemplo.granjaavicola.repository.GalpaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GalpaoService {

    private final GalpaoRepository repository;

    public GalpaoService(GalpaoRepository repository) {
        this.repository = repository;
    }

    public List<Galpao> listar() {
        return repository.findAll();
    }

    public Optional<Galpao> buscar(Long id) {
        return repository.findById(id);
    }

    public Galpao criar(Galpao galpao) {
        return repository.save(galpao);
    }

    public Optional<Galpao> atualizar(Long id, Galpao galpao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        galpao.setId(id);
        return Optional.of(repository.save(galpao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
