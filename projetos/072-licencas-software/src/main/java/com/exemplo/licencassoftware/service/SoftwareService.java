package com.exemplo.licencassoftware.service;

import com.exemplo.licencassoftware.model.Software;
import com.exemplo.licencassoftware.repository.SoftwareRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SoftwareService {

    private final SoftwareRepository repository;

    public SoftwareService(SoftwareRepository repository) {
        this.repository = repository;
    }

    public List<Software> listar() {
        return repository.findAll();
    }

    public Optional<Software> buscar(Long id) {
        return repository.findById(id);
    }

    public Software criar(Software software) {
        return repository.save(software);
    }

    public Optional<Software> atualizar(Long id, Software software) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        software.setId(id);
        return Optional.of(repository.save(software));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
