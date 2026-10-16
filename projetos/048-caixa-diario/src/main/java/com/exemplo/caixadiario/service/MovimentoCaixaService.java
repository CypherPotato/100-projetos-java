package com.exemplo.caixadiario.service;

import com.exemplo.caixadiario.model.MovimentoCaixa;
import com.exemplo.caixadiario.repository.MovimentoCaixaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MovimentoCaixaService {

    private final MovimentoCaixaRepository repository;

    public MovimentoCaixaService(MovimentoCaixaRepository repository) {
        this.repository = repository;
    }

    public List<MovimentoCaixa> listar(Long caixaId) {
        if (caixaId != null) {
            return repository.findByCaixaId(caixaId);
        }
        return repository.findAll();
    }

    public Optional<MovimentoCaixa> buscar(Long id) {
        return repository.findById(id);
    }

    public MovimentoCaixa criar(MovimentoCaixa movimentoCaixa) {
        return repository.save(movimentoCaixa);
    }

    public Optional<MovimentoCaixa> atualizar(Long id, MovimentoCaixa movimentoCaixa) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        movimentoCaixa.setId(id);
        return Optional.of(repository.save(movimentoCaixa));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
