package com.exemplo.centrodistribuicao.service;

import com.exemplo.centrodistribuicao.model.Armazem;
import com.exemplo.centrodistribuicao.repository.ArmazemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ArmazemService {

    private final ArmazemRepository repository;

    public ArmazemService(ArmazemRepository repository) {
        this.repository = repository;
    }

    public List<Armazem> listar() {
        return repository.findAll();
    }

    public Optional<Armazem> buscar(Long id) {
        return repository.findById(id);
    }

    public Armazem criar(Armazem armazem) {
        return repository.save(armazem);
    }

    public Optional<Armazem> atualizar(Long id, Armazem armazem) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        armazem.setId(id);
        return Optional.of(repository.save(armazem));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
