package com.exemplo.forum.service;

import com.exemplo.forum.model.Topico;
import com.exemplo.forum.repository.TopicoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    private final TopicoRepository repository;

    public TopicoService(TopicoRepository repository) {
        this.repository = repository;
    }

    public List<Topico> listar(Long autorId) {
        if (autorId != null) {
            return repository.findByAutorId(autorId);
        }
        return repository.findAll();
    }

    public Optional<Topico> buscar(Long id) {
        return repository.findById(id);
    }

    public Topico criar(Topico topico) {
        return repository.save(topico);
    }

    public Optional<Topico> atualizar(Long id, Topico topico) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        topico.setId(id);
        return Optional.of(repository.save(topico));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
