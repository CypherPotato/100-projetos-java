package com.exemplo.controlecontratos.service;

import com.exemplo.controlecontratos.model.Contrato;
import com.exemplo.controlecontratos.repository.ContratoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ContratoService {

    private final ContratoRepository repository;

    public ContratoService(ContratoRepository repository) {
        this.repository = repository;
    }

    public List<Contrato> listar(Long empresaId) {
        if (empresaId != null) {
            return repository.findByEmpresaId(empresaId);
        }
        return repository.findAll();
    }

    public Optional<Contrato> buscar(Long id) {
        return repository.findById(id);
    }

    public Contrato criar(Contrato contrato) {
        return repository.save(contrato);
    }

    public Optional<Contrato> atualizar(Long id, Contrato contrato) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        contrato.setId(id);
        return Optional.of(repository.save(contrato));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
