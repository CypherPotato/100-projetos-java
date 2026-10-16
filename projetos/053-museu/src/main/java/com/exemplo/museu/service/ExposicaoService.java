package com.exemplo.museu.service;

import com.exemplo.museu.model.Exposicao;
import com.exemplo.museu.repository.ExposicaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ExposicaoService {

    private final ExposicaoRepository repository;

    public ExposicaoService(ExposicaoRepository repository) {
        this.repository = repository;
    }

    public List<Exposicao> listar() {
        return repository.findAll();
    }

    public Optional<Exposicao> buscar(Long id) {
        return repository.findById(id);
    }

    public Exposicao criar(Exposicao exposicao) {
        return repository.save(exposicao);
    }

    public Optional<Exposicao> atualizar(Long id, Exposicao exposicao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        exposicao.setId(id);
        return Optional.of(repository.save(exposicao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
