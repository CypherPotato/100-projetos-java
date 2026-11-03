package com.exemplo.gibiteca.service;

import com.exemplo.gibiteca.model.Revista;
import com.exemplo.gibiteca.repository.RevistaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RevistaService {

    private final RevistaRepository repository;

    public RevistaService(RevistaRepository repository) {
        this.repository = repository;
    }

    public List<Revista> listar(Long colecaoId) {
        if (colecaoId != null) {
            return repository.findByColecaoId(colecaoId);
        }
        return repository.findAll();
    }

    public Optional<Revista> buscar(Long id) {
        return repository.findById(id);
    }

    public Revista criar(Revista revista) {
        return repository.save(revista);
    }

    public Optional<Revista> atualizar(Long id, Revista revista) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        revista.setId(id);
        return Optional.of(repository.save(revista));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
