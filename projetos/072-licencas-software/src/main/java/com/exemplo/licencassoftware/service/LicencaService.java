package com.exemplo.licencassoftware.service;

import com.exemplo.licencassoftware.model.Licenca;
import com.exemplo.licencassoftware.repository.LicencaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LicencaService {

    private final LicencaRepository repository;

    public LicencaService(LicencaRepository repository) {
        this.repository = repository;
    }

    public List<Licenca> listar(Long softwareId) {
        if (softwareId != null) {
            return repository.findBySoftwareId(softwareId);
        }
        return repository.findAll();
    }

    public Optional<Licenca> buscar(Long id) {
        return repository.findById(id);
    }

    public Licenca criar(Licenca licenca) {
        return repository.save(licenca);
    }

    public Optional<Licenca> atualizar(Long id, Licenca licenca) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        licenca.setId(id);
        return Optional.of(repository.save(licenca));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
