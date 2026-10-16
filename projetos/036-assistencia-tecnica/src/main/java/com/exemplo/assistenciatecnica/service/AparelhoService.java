package com.exemplo.assistenciatecnica.service;

import com.exemplo.assistenciatecnica.model.Aparelho;
import com.exemplo.assistenciatecnica.repository.AparelhoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AparelhoService {

    private final AparelhoRepository repository;

    public AparelhoService(AparelhoRepository repository) {
        this.repository = repository;
    }

    public List<Aparelho> listar(Long clienteId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    public Optional<Aparelho> buscar(Long id) {
        return repository.findById(id);
    }

    public Aparelho criar(Aparelho aparelho) {
        return repository.save(aparelho);
    }

    public Optional<Aparelho> atualizar(Long id, Aparelho aparelho) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        aparelho.setId(id);
        return Optional.of(repository.save(aparelho));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
