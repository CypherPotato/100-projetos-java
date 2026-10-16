package com.exemplo.padaria.service;

import com.exemplo.padaria.model.Padeiro;
import com.exemplo.padaria.repository.PadeiroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PadeiroService {

    private final PadeiroRepository repository;

    public PadeiroService(PadeiroRepository repository) {
        this.repository = repository;
    }

    public List<Padeiro> listar() {
        return repository.findAll();
    }

    public Optional<Padeiro> buscar(Long id) {
        return repository.findById(id);
    }

    public Padeiro criar(Padeiro padeiro) {
        return repository.save(padeiro);
    }

    public Optional<Padeiro> atualizar(Long id, Padeiro padeiro) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        padeiro.setId(id);
        return Optional.of(repository.save(padeiro));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
