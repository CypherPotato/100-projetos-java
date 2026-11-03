package com.exemplo.consorcio.service;

import com.exemplo.consorcio.model.Grupo;
import com.exemplo.consorcio.repository.GrupoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GrupoService {

    private final GrupoRepository repository;

    public GrupoService(GrupoRepository repository) {
        this.repository = repository;
    }

    public List<Grupo> listar() {
        return repository.findAll();
    }

    public Optional<Grupo> buscar(Long id) {
        return repository.findById(id);
    }

    public Grupo criar(Grupo grupo) {
        return repository.save(grupo);
    }

    public Optional<Grupo> atualizar(Long id, Grupo grupo) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        grupo.setId(id);
        return Optional.of(repository.save(grupo));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
