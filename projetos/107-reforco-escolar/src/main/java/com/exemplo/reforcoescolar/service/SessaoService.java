package com.exemplo.reforcoescolar.service;

import com.exemplo.reforcoescolar.model.Sessao;
import com.exemplo.reforcoescolar.repository.SessaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SessaoService {

    private final SessaoRepository repository;

    public SessaoService(SessaoRepository repository) {
        this.repository = repository;
    }

    public List<Sessao> listar(Long alunoId) {
        if (alunoId != null) {
            return repository.findByAlunoId(alunoId);
        }
        return repository.findAll();
    }

    public Optional<Sessao> buscar(Long id) {
        return repository.findById(id);
    }

    public Sessao criar(Sessao sessao) {
        return repository.save(sessao);
    }

    public Optional<Sessao> atualizar(Long id, Sessao sessao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        sessao.setId(id);
        return Optional.of(repository.save(sessao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
