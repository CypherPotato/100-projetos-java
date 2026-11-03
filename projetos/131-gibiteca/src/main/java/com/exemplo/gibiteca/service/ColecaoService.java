package com.exemplo.gibiteca.service;

import com.exemplo.gibiteca.model.Colecao;
import com.exemplo.gibiteca.repository.ColecaoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ColecaoService {

    private final ColecaoRepository repository;

    public ColecaoService(ColecaoRepository repository) {
        this.repository = repository;
    }

    public List<Colecao> listar() {
        return repository.findAll();
    }

    public Optional<Colecao> buscar(Long id) {
        return repository.findById(id);
    }

    public Colecao criar(Colecao colecao) {
        return repository.save(colecao);
    }

    public Optional<Colecao> atualizar(Long id, Colecao colecao) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        colecao.setId(id);
        return Optional.of(repository.save(colecao));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
