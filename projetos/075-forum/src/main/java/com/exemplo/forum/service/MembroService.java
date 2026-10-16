package com.exemplo.forum.service;

import com.exemplo.forum.model.Membro;
import com.exemplo.forum.repository.MembroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MembroService {

    private final MembroRepository repository;

    public MembroService(MembroRepository repository) {
        this.repository = repository;
    }

    public List<Membro> listar() {
        return repository.findAll();
    }

    public Optional<Membro> buscar(Long id) {
        return repository.findById(id);
    }

    public Membro criar(Membro membro) {
        return repository.save(membro);
    }

    public Optional<Membro> atualizar(Long id, Membro membro) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        membro.setId(id);
        return Optional.of(repository.save(membro));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
