package com.exemplo.controlecontratos.service;

import com.exemplo.controlecontratos.model.Empresa;
import com.exemplo.controlecontratos.repository.EmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    private final EmpresaRepository repository;

    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    public List<Empresa> listar() {
        return repository.findAll();
    }

    public Optional<Empresa> buscar(Long id) {
        return repository.findById(id);
    }

    public Empresa criar(Empresa empresa) {
        return repository.save(empresa);
    }

    public Optional<Empresa> atualizar(Long id, Empresa empresa) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        empresa.setId(id);
        return Optional.of(repository.save(empresa));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
