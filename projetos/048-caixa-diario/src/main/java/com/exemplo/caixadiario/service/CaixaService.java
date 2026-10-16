package com.exemplo.caixadiario.service;

import com.exemplo.caixadiario.model.Caixa;
import com.exemplo.caixadiario.repository.CaixaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CaixaService {

    private final CaixaRepository repository;

    public CaixaService(CaixaRepository repository) {
        this.repository = repository;
    }

    public List<Caixa> listar() {
        return repository.findAll();
    }

    public Optional<Caixa> buscar(Long id) {
        return repository.findById(id);
    }

    public Caixa criar(Caixa caixa) {
        return repository.save(caixa);
    }

    public Optional<Caixa> atualizar(Long id, Caixa caixa) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        caixa.setId(id);
        return Optional.of(repository.save(caixa));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
