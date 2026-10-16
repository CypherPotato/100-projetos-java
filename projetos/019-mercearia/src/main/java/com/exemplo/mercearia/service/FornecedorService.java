package com.exemplo.mercearia.service;

import com.exemplo.mercearia.model.Fornecedor;
import com.exemplo.mercearia.repository.FornecedorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    public List<Fornecedor> listar() {
        return repository.findAll();
    }

    public Optional<Fornecedor> buscar(Long id) {
        return repository.findById(id);
    }

    public Fornecedor criar(Fornecedor fornecedor) {
        return repository.save(fornecedor);
    }

    public Optional<Fornecedor> atualizar(Long id, Fornecedor fornecedor) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        fornecedor.setId(id);
        return Optional.of(repository.save(fornecedor));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
