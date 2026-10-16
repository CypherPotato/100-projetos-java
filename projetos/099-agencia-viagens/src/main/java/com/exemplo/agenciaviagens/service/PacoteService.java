package com.exemplo.agenciaviagens.service;

import com.exemplo.agenciaviagens.model.Pacote;
import com.exemplo.agenciaviagens.repository.PacoteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PacoteService {

    private final PacoteRepository repository;

    public PacoteService(PacoteRepository repository) {
        this.repository = repository;
    }

    public List<Pacote> listar() {
        return repository.findAll();
    }

    public Optional<Pacote> buscar(Long id) {
        return repository.findById(id);
    }

    public Pacote criar(Pacote pacote) {
        return repository.save(pacote);
    }

    public Optional<Pacote> atualizar(Long id, Pacote pacote) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        pacote.setId(id);
        return Optional.of(repository.save(pacote));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
