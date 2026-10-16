package com.exemplo.salaobeleza.service;

import com.exemplo.salaobeleza.model.Atendimento;
import com.exemplo.salaobeleza.repository.AtendimentoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AtendimentoService {

    private final AtendimentoRepository repository;

    public AtendimentoService(AtendimentoRepository repository) {
        this.repository = repository;
    }

    public List<Atendimento> listar(Long clienteId, Long procedimentoId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        if (procedimentoId != null) {
            return repository.findByProcedimentoId(procedimentoId);
        }
        return repository.findAll();
    }

    public Optional<Atendimento> buscar(Long id) {
        return repository.findById(id);
    }

    public Atendimento criar(Atendimento atendimento) {
        return repository.save(atendimento);
    }

    public Optional<Atendimento> atualizar(Long id, Atendimento atendimento) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        atendimento.setId(id);
        return Optional.of(repository.save(atendimento));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
