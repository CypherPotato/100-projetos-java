package com.exemplo.rebanho.service;

import com.exemplo.rebanho.model.Lote;
import com.exemplo.rebanho.repository.LoteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LoteService {

    private final LoteRepository repository;

    public LoteService(LoteRepository repository) {
        this.repository = repository;
    }

    public List<Lote> listar() {
        return repository.findAll();
    }

    public Optional<Lote> buscar(Long id) {
        return repository.findById(id);
    }

    public Lote criar(Lote lote) {
        return repository.save(lote);
    }

    public Optional<Lote> atualizar(Long id, Lote lote) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        lote.setId(id);
        return Optional.of(repository.save(lote));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
