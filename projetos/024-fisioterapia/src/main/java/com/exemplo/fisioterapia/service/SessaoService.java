package com.exemplo.fisioterapia.service;

import com.exemplo.fisioterapia.model.Sessao;
import com.exemplo.fisioterapia.repository.SessaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SessaoService {

    private final SessaoRepository repository;

    public SessaoService(SessaoRepository repository) {
        this.repository = repository;
    }

    public List<Sessao> listar(Long fisioterapeutaId) {
        if (fisioterapeutaId != null) {
            return repository.findByFisioterapeutaId(fisioterapeutaId);
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
