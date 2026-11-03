package com.exemplo.casashows.service;

import com.exemplo.casashows.model.Artista;
import com.exemplo.casashows.repository.ArtistaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ArtistaService {

    private final ArtistaRepository repository;

    public ArtistaService(ArtistaRepository repository) {
        this.repository = repository;
    }

    public List<Artista> listar() {
        return repository.findAll();
    }

    public Optional<Artista> buscar(Long id) {
        return repository.findById(id);
    }

    public Artista criar(Artista artista) {
        return repository.save(artista);
    }

    public Optional<Artista> atualizar(Long id, Artista artista) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        artista.setId(id);
        return Optional.of(repository.save(artista));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
