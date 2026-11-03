package com.exemplo.centrodistribuicao.service;

import com.exemplo.centrodistribuicao.model.Expedicao;
import com.exemplo.centrodistribuicao.repository.ExpedicaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ExpedicaoService {

    private final ExpedicaoRepository repository;

    public ExpedicaoService(ExpedicaoRepository repository) {
        this.repository = repository;
    }

    public List<Expedicao> listar(Long mercadoriaId) {
        if (mercadoriaId != null) {
            return repository.findByMercadoriaId(mercadoriaId);
        }
        return repository.findAll();
    }

    public Optional<Expedicao> buscar(Long id) {
        return repository.findById(id);
    }

    public Expedicao criar(Expedicao expedicao) {
        return repository.save(expedicao);
    }

    public Optional<Expedicao> atualizar(Long id, Expedicao expedicao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        expedicao.setId(id);
        return Optional.of(repository.save(expedicao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
