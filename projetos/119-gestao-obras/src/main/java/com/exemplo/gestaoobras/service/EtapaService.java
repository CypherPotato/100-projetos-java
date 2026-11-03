package com.exemplo.gestaoobras.service;

import com.exemplo.gestaoobras.model.Etapa;
import com.exemplo.gestaoobras.repository.EtapaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EtapaService {

    private final EtapaRepository repository;

    public EtapaService(EtapaRepository repository) {
        this.repository = repository;
    }

    public List<Etapa> listar(Long obraId) {
        if (obraId != null) {
            return repository.findByObraId(obraId);
        }
        return repository.findAll();
    }

    public Optional<Etapa> buscar(Long id) {
        return repository.findById(id);
    }

    public Etapa criar(Etapa etapa) {
        return repository.save(etapa);
    }

    public Optional<Etapa> atualizar(Long id, Etapa etapa) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        etapa.setId(id);
        return Optional.of(repository.save(etapa));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
