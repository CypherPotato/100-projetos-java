package com.exemplo.eventos.service;

import com.exemplo.eventos.model.Evento;
import com.exemplo.eventos.repository.EventoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    private final EventoRepository repository;

    public EventoService(EventoRepository repository) {
        this.repository = repository;
    }

    public List<Evento> listar() {
        return repository.findAll();
    }

    public Optional<Evento> buscar(Long id) {
        return repository.findById(id);
    }

    public Evento criar(Evento evento) {
        return repository.save(evento);
    }

    public Optional<Evento> atualizar(Long id, Evento evento) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        evento.setId(id);
        return Optional.of(repository.save(evento));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
