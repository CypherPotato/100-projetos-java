package com.exemplo.clinicaestetica.service;

import com.exemplo.clinicaestetica.model.Procedimento;
import com.exemplo.clinicaestetica.repository.ProcedimentoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProcedimentoService {

    private final ProcedimentoRepository repository;

    public ProcedimentoService(ProcedimentoRepository repository) {
        this.repository = repository;
    }

    public List<Procedimento> listar() {
        return repository.findAll();
    }

    public Optional<Procedimento> buscar(Long id) {
        return repository.findById(id);
    }

    public Procedimento criar(Procedimento procedimento) {
        return repository.save(procedimento);
    }

    public Optional<Procedimento> atualizar(Long id, Procedimento procedimento) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        procedimento.setId(id);
        return Optional.of(repository.save(procedimento));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
