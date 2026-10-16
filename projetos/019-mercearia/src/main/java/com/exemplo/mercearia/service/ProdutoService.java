package com.exemplo.mercearia.service;

import com.exemplo.mercearia.model.Produto;
import com.exemplo.mercearia.repository.ProdutoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listar(Long fornecedorId) {
        if (fornecedorId != null) {
            return repository.findByFornecedorId(fornecedorId);
        }
        return repository.findAll();
    }

    public Optional<Produto> buscar(Long id) {
        return repository.findById(id);
    }

    public Produto criar(Produto produto) {
        return repository.save(produto);
    }

    public Optional<Produto> atualizar(Long id, Produto produto) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        produto.setId(id);
        return Optional.of(repository.save(produto));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
