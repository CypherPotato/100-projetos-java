package com.exemplo.museu.service;

import com.exemplo.museu.model.Visita;
import com.exemplo.museu.repository.VisitaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class VisitaService {

    private final VisitaRepository repository;

    public VisitaService(VisitaRepository repository) {
        this.repository = repository;
    }

    public List<Visita> listar(Long exposicaoId) {
        if (exposicaoId != null) {
            return repository.findByExposicaoId(exposicaoId);
        }
        return repository.findAll();
    }

    public Optional<Visita> buscar(Long id) {
        return repository.findById(id);
    }

    public Visita criar(Visita visita) {
        return repository.save(visita);
    }

    public Optional<Visita> atualizar(Long id, Visita visita) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        visita.setId(id);
        return Optional.of(repository.save(visita));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
