package com.exemplo.eventos.service;

import com.exemplo.eventos.model.Inscricao;
import com.exemplo.eventos.repository.InscricaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class InscricaoService {

    private final InscricaoRepository repository;

    public InscricaoService(InscricaoRepository repository) {
        this.repository = repository;
    }

    public List<Inscricao> listar(Long eventoId) {
        if (eventoId != null) {
            return repository.findByEventoId(eventoId);
        }
        return repository.findAll();
    }

    public Optional<Inscricao> buscar(Long id) {
        return repository.findById(id);
    }

    public Inscricao criar(Inscricao inscricao) {
        return repository.save(inscricao);
    }

    public Optional<Inscricao> atualizar(Long id, Inscricao inscricao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        inscricao.setId(id);
        return Optional.of(repository.save(inscricao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
