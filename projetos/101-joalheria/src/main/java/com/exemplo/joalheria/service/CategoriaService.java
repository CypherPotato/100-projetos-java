package com.exemplo.joalheria.service;

import com.exemplo.joalheria.model.Categoria;
import com.exemplo.joalheria.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public Optional<Categoria> buscar(Long id) {
        return repository.findById(id);
    }

    public Categoria criar(Categoria categoria) {
        return repository.save(categoria);
    }

    public Optional<Categoria> atualizar(Long id, Categoria categoria) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        categoria.setId(id);
        return Optional.of(repository.save(categoria));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
