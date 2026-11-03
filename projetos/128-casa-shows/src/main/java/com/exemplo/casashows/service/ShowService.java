package com.exemplo.casashows.service;

import com.exemplo.casashows.model.Show;
import com.exemplo.casashows.repository.ShowRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ShowService {

    private final ShowRepository repository;

    public ShowService(ShowRepository repository) {
        this.repository = repository;
    }

    public List<Show> listar(Long artistaId) {
        if (artistaId != null) {
            return repository.findByArtistaId(artistaId);
        }
        return repository.findAll();
    }

    public Optional<Show> buscar(Long id) {
        return repository.findById(id);
    }

    public Show criar(Show show) {
        return repository.save(show);
    }

    public Optional<Show> atualizar(Long id, Show show) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        show.setId(id);
        return Optional.of(repository.save(show));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
