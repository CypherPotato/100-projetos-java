package com.exemplo.forum.service;

import com.exemplo.forum.model.Resposta;
import com.exemplo.forum.repository.RespostaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RespostaService {

    private final RespostaRepository repository;

    public RespostaService(RespostaRepository repository) {
        this.repository = repository;
    }

    public List<Resposta> listar(Long topicoId, Long autorId) {
        if (topicoId != null) {
            return repository.findByTopicoId(topicoId);
        }
        if (autorId != null) {
            return repository.findByAutorId(autorId);
        }
        return repository.findAll();
    }

    public Optional<Resposta> buscar(Long id) {
        return repository.findById(id);
    }

    public Resposta criar(Resposta resposta) {
        return repository.save(resposta);
    }

    public Optional<Resposta> atualizar(Long id, Resposta resposta) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        resposta.setId(id);
        return Optional.of(repository.save(resposta));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
