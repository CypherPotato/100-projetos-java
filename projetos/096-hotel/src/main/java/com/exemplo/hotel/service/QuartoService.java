package com.exemplo.hotel.service;

import com.exemplo.hotel.model.Quarto;
import com.exemplo.hotel.repository.QuartoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class QuartoService {

    private final QuartoRepository repository;

    public QuartoService(QuartoRepository repository) {
        this.repository = repository;
    }

    public List<Quarto> listar() {
        return repository.findAll();
    }

    public Optional<Quarto> buscar(Long id) {
        return repository.findById(id);
    }

    public Quarto criar(Quarto quarto) {
        return repository.save(quarto);
    }

    public Optional<Quarto> atualizar(Long id, Quarto quarto) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        quarto.setId(id);
        return Optional.of(repository.save(quarto));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
