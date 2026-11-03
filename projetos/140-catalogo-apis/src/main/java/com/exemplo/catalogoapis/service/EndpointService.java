package com.exemplo.catalogoapis.service;

import com.exemplo.catalogoapis.model.Endpoint;
import com.exemplo.catalogoapis.repository.EndpointRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EndpointService {

    private final EndpointRepository repository;

    public EndpointService(EndpointRepository repository) {
        this.repository = repository;
    }

    public List<Endpoint> listar(Long apiId) {
        if (apiId != null) {
            return repository.findByApiId(apiId);
        }
        return repository.findAll();
    }

    public Optional<Endpoint> buscar(Long id) {
        return repository.findById(id);
    }

    public Endpoint criar(Endpoint endpoint) {
        return repository.save(endpoint);
    }

    public Optional<Endpoint> atualizar(Long id, Endpoint endpoint) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        endpoint.setId(id);
        return Optional.of(repository.save(endpoint));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
