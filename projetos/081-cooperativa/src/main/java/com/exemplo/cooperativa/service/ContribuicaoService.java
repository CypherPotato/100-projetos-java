package com.exemplo.cooperativa.service;

import com.exemplo.cooperativa.model.Contribuicao;
import com.exemplo.cooperativa.repository.ContribuicaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ContribuicaoService {

    private final ContribuicaoRepository repository;

    public ContribuicaoService(ContribuicaoRepository repository) {
        this.repository = repository;
    }

    public List<Contribuicao> listar(Long cooperadoId) {
        if (cooperadoId != null) {
            return repository.findByCooperadoId(cooperadoId);
        }
        return repository.findAll();
    }

    public Optional<Contribuicao> buscar(Long id) {
        return repository.findById(id);
    }

    public Contribuicao criar(Contribuicao contribuicao) {
        return repository.save(contribuicao);
    }

    public Optional<Contribuicao> atualizar(Long id, Contribuicao contribuicao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        contribuicao.setId(id);
        return Optional.of(repository.save(contribuicao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
