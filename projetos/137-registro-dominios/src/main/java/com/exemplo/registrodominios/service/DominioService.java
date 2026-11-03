package com.exemplo.registrodominios.service;

import com.exemplo.registrodominios.model.Dominio;
import com.exemplo.registrodominios.repository.DominioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DominioService {

    private final DominioRepository repository;

    public DominioService(DominioRepository repository) {
        this.repository = repository;
    }

    public List<Dominio> listar(Long registradorId) {
        if (registradorId != null) {
            return repository.findByRegistradorId(registradorId);
        }
        return repository.findAll();
    }

    public Optional<Dominio> buscar(Long id) {
        return repository.findById(id);
    }

    public Dominio criar(Dominio dominio) {
        return repository.save(dominio);
    }

    public Optional<Dominio> atualizar(Long id, Dominio dominio) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        dominio.setId(id);
        return Optional.of(repository.save(dominio));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
