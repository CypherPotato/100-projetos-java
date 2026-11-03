package com.exemplo.protocolodocumentos.service;

import com.exemplo.protocolodocumentos.model.Setor;
import com.exemplo.protocolodocumentos.repository.SetorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SetorService {

    private final SetorRepository repository;

    public SetorService(SetorRepository repository) {
        this.repository = repository;
    }

    public List<Setor> listar() {
        return repository.findAll();
    }

    public Optional<Setor> buscar(Long id) {
        return repository.findById(id);
    }

    public Setor criar(Setor setor) {
        return repository.save(setor);
    }

    public Optional<Setor> atualizar(Long id, Setor setor) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        setor.setId(id);
        return Optional.of(repository.save(setor));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
