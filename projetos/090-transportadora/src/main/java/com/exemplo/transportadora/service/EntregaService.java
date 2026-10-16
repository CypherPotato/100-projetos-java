package com.exemplo.transportadora.service;

import com.exemplo.transportadora.model.Entrega;
import com.exemplo.transportadora.repository.EntregaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EntregaService {

    private final EntregaRepository repository;

    public EntregaService(EntregaRepository repository) {
        this.repository = repository;
    }

    public List<Entrega> listar(Long motoristaId, Long veiculoId) {
        if (motoristaId != null) {
            return repository.findByMotoristaId(motoristaId);
        }
        if (veiculoId != null) {
            return repository.findByVeiculoId(veiculoId);
        }
        return repository.findAll();
    }

    public Optional<Entrega> buscar(Long id) {
        return repository.findById(id);
    }

    public Entrega criar(Entrega entrega) {
        return repository.save(entrega);
    }

    public Optional<Entrega> atualizar(Long id, Entrega entrega) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        entrega.setId(id);
        return Optional.of(repository.save(entrega));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
