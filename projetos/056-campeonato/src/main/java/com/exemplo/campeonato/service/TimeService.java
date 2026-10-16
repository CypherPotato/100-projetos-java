package com.exemplo.campeonato.service;

import com.exemplo.campeonato.model.Time;
import com.exemplo.campeonato.repository.TimeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TimeService {

    private final TimeRepository repository;

    public TimeService(TimeRepository repository) {
        this.repository = repository;
    }

    public List<Time> listar() {
        return repository.findAll();
    }

    public Optional<Time> buscar(Long id) {
        return repository.findById(id);
    }

    public Time criar(Time time) {
        return repository.save(time);
    }

    public Optional<Time> atualizar(Long id, Time time) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        time.setId(id);
        return Optional.of(repository.save(time));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
