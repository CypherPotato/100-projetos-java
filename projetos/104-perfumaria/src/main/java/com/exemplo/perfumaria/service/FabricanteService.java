package com.exemplo.perfumaria.service;

import com.exemplo.perfumaria.model.Fabricante;
import com.exemplo.perfumaria.repository.FabricanteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FabricanteService {

    private final FabricanteRepository repository;

    public FabricanteService(FabricanteRepository repository) {
        this.repository = repository;
    }

    public List<Fabricante> listar() {
        return repository.findAll();
    }

    public Optional<Fabricante> buscar(Long id) {
        return repository.findById(id);
    }

    public Fabricante criar(Fabricante fabricante) {
        return repository.save(fabricante);
    }

    public Optional<Fabricante> atualizar(Long id, Fabricante fabricante) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        fabricante.setId(id);
        return Optional.of(repository.save(fabricante));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
