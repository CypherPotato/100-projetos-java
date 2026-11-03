package com.exemplo.catalogoapis.service;

import com.exemplo.catalogoapis.model.Api;
import com.exemplo.catalogoapis.repository.ApiRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    private final ApiRepository repository;

    public ApiService(ApiRepository repository) {
        this.repository = repository;
    }

    public List<Api> listar(Long equipeId) {
        if (equipeId != null) {
            return repository.findByEquipeId(equipeId);
        }
        return repository.findAll();
    }

    public Optional<Api> buscar(Long id) {
        return repository.findById(id);
    }

    public Api criar(Api api) {
        return repository.save(api);
    }

    public Optional<Api> atualizar(Long id, Api api) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        api.setId(id);
        return Optional.of(repository.save(api));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
