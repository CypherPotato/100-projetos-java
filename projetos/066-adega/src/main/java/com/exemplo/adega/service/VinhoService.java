package com.exemplo.adega.service;

import com.exemplo.adega.model.Vinho;
import com.exemplo.adega.repository.VinhoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class VinhoService {

    private final VinhoRepository repository;

    public VinhoService(VinhoRepository repository) {
        this.repository = repository;
    }

    public List<Vinho> listar(Long vinicolaId) {
        if (vinicolaId != null) {
            return repository.findByVinicolaId(vinicolaId);
        }
        return repository.findAll();
    }

    public Optional<Vinho> buscar(Long id) {
        return repository.findById(id);
    }

    public Vinho criar(Vinho vinho) {
        return repository.save(vinho);
    }

    public Optional<Vinho> atualizar(Long id, Vinho vinho) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        vinho.setId(id);
        return Optional.of(repository.save(vinho));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
