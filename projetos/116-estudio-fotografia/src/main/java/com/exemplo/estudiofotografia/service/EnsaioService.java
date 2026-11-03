package com.exemplo.estudiofotografia.service;

import com.exemplo.estudiofotografia.model.Ensaio;
import com.exemplo.estudiofotografia.repository.EnsaioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EnsaioService {

    private final EnsaioRepository repository;

    public EnsaioService(EnsaioRepository repository) {
        this.repository = repository;
    }

    public List<Ensaio> listar(Long clienteId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    public Optional<Ensaio> buscar(Long id) {
        return repository.findById(id);
    }

    public Ensaio criar(Ensaio ensaio) {
        return repository.save(ensaio);
    }

    public Optional<Ensaio> atualizar(Long id, Ensaio ensaio) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        ensaio.setId(id);
        return Optional.of(repository.save(ensaio));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
