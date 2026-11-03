package com.exemplo.registrodominios.service;

import com.exemplo.registrodominios.model.Registrador;
import com.exemplo.registrodominios.repository.RegistradorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RegistradorService {

    private final RegistradorRepository repository;

    public RegistradorService(RegistradorRepository repository) {
        this.repository = repository;
    }

    public List<Registrador> listar() {
        return repository.findAll();
    }

    public Optional<Registrador> buscar(Long id) {
        return repository.findById(id);
    }

    public Registrador criar(Registrador registrador) {
        return repository.save(registrador);
    }

    public Optional<Registrador> atualizar(Long id, Registrador registrador) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        registrador.setId(id);
        return Optional.of(repository.save(registrador));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
