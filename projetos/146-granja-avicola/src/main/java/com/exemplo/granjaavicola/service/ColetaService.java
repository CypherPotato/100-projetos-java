package com.exemplo.granjaavicola.service;

import com.exemplo.granjaavicola.model.Coleta;
import com.exemplo.granjaavicola.repository.ColetaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ColetaService {

    private final ColetaRepository repository;

    public ColetaService(ColetaRepository repository) {
        this.repository = repository;
    }

    public List<Coleta> listar(Long loteId) {
        if (loteId != null) {
            return repository.findByLoteId(loteId);
        }
        return repository.findAll();
    }

    public Optional<Coleta> buscar(Long id) {
        return repository.findById(id);
    }

    public Coleta criar(Coleta coleta) {
        return repository.save(coleta);
    }

    public Optional<Coleta> atualizar(Long id, Coleta coleta) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        coleta.setId(id);
        return Optional.of(repository.save(coleta));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
