package com.exemplo.lojaroupas.service;

import com.exemplo.lojaroupas.model.Roupa;
import com.exemplo.lojaroupas.repository.RoupaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RoupaService {

    private final RoupaRepository repository;

    public RoupaService(RoupaRepository repository) {
        this.repository = repository;
    }

    public List<Roupa> listar(Long categoriaId) {
        if (categoriaId != null) {
            return repository.findByCategoriaId(categoriaId);
        }
        return repository.findAll();
    }

    public Optional<Roupa> buscar(Long id) {
        return repository.findById(id);
    }

    public Roupa criar(Roupa roupa) {
        return repository.save(roupa);
    }

    public Optional<Roupa> atualizar(Long id, Roupa roupa) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        roupa.setId(id);
        return Optional.of(repository.save(roupa));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
