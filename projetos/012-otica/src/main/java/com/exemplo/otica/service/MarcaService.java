package com.exemplo.otica.service;

import com.exemplo.otica.model.Marca;
import com.exemplo.otica.repository.MarcaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {

    private final MarcaRepository repository;

    public MarcaService(MarcaRepository repository) {
        this.repository = repository;
    }

    public List<Marca> listar() {
        return repository.findAll();
    }

    public Optional<Marca> buscar(Long id) {
        return repository.findById(id);
    }

    public Marca criar(Marca marca) {
        return repository.save(marca);
    }

    public Optional<Marca> atualizar(Long id, Marca marca) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        marca.setId(id);
        return Optional.of(repository.save(marca));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
