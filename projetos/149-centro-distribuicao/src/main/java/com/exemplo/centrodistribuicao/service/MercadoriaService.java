package com.exemplo.centrodistribuicao.service;

import com.exemplo.centrodistribuicao.model.Mercadoria;
import com.exemplo.centrodistribuicao.repository.MercadoriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MercadoriaService {

    private final MercadoriaRepository repository;

    public MercadoriaService(MercadoriaRepository repository) {
        this.repository = repository;
    }

    public List<Mercadoria> listar(Long armazemId) {
        if (armazemId != null) {
            return repository.findByArmazemId(armazemId);
        }
        return repository.findAll();
    }

    public Optional<Mercadoria> buscar(Long id) {
        return repository.findById(id);
    }

    public Mercadoria criar(Mercadoria mercadoria) {
        return repository.save(mercadoria);
    }

    public Optional<Mercadoria> atualizar(Long id, Mercadoria mercadoria) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        mercadoria.setId(id);
        return Optional.of(repository.save(mercadoria));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
