package com.exemplo.gestaoobras.service;

import com.exemplo.gestaoobras.model.Obra;
import com.exemplo.gestaoobras.repository.ObraRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ObraService {

    private final ObraRepository repository;

    public ObraService(ObraRepository repository) {
        this.repository = repository;
    }

    public List<Obra> listar() {
        return repository.findAll();
    }

    public Optional<Obra> buscar(Long id) {
        return repository.findById(id);
    }

    public Obra criar(Obra obra) {
        return repository.save(obra);
    }

    public Optional<Obra> atualizar(Long id, Obra obra) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        obra.setId(id);
        return Optional.of(repository.save(obra));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
