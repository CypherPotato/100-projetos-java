package com.exemplo.lavarapido.service;

import com.exemplo.lavarapido.model.Lavagem;
import com.exemplo.lavarapido.repository.LavagemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LavagemService {

    private final LavagemRepository repository;

    public LavagemService(LavagemRepository repository) {
        this.repository = repository;
    }

    public List<Lavagem> listar(Long veiculoId) {
        if (veiculoId != null) {
            return repository.findByVeiculoId(veiculoId);
        }
        return repository.findAll();
    }

    public Optional<Lavagem> buscar(Long id) {
        return repository.findById(id);
    }

    public Lavagem criar(Lavagem lavagem) {
        return repository.save(lavagem);
    }

    public Optional<Lavagem> atualizar(Long id, Lavagem lavagem) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        lavagem.setId(id);
        return Optional.of(repository.save(lavagem));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
