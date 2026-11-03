package com.exemplo.escoteiros.service;

import com.exemplo.escoteiros.model.Escoteiro;
import com.exemplo.escoteiros.repository.EscoteiroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EscoteiroService {

    private final EscoteiroRepository repository;

    public EscoteiroService(EscoteiroRepository repository) {
        this.repository = repository;
    }

    public List<Escoteiro> listar(Long patrulhaId) {
        if (patrulhaId != null) {
            return repository.findByPatrulhaId(patrulhaId);
        }
        return repository.findAll();
    }

    public Optional<Escoteiro> buscar(Long id) {
        return repository.findById(id);
    }

    public Escoteiro criar(Escoteiro escoteiro) {
        return repository.save(escoteiro);
    }

    public Optional<Escoteiro> atualizar(Long id, Escoteiro escoteiro) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        escoteiro.setId(id);
        return Optional.of(repository.save(escoteiro));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
