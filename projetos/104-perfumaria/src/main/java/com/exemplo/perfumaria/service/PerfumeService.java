package com.exemplo.perfumaria.service;

import com.exemplo.perfumaria.model.Perfume;
import com.exemplo.perfumaria.repository.PerfumeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PerfumeService {

    private final PerfumeRepository repository;

    public PerfumeService(PerfumeRepository repository) {
        this.repository = repository;
    }

    public List<Perfume> listar(Long fabricanteId) {
        if (fabricanteId != null) {
            return repository.findByFabricanteId(fabricanteId);
        }
        return repository.findAll();
    }

    public Optional<Perfume> buscar(Long id) {
        return repository.findById(id);
    }

    public Perfume criar(Perfume perfume) {
        return repository.save(perfume);
    }

    public Optional<Perfume> atualizar(Long id, Perfume perfume) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        perfume.setId(id);
        return Optional.of(repository.save(perfume));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
