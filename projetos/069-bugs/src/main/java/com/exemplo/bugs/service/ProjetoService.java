package com.exemplo.bugs.service;

import com.exemplo.bugs.model.Projeto;
import com.exemplo.bugs.repository.ProjetoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProjetoService {

    private final ProjetoRepository repository;

    public ProjetoService(ProjetoRepository repository) {
        this.repository = repository;
    }

    public List<Projeto> listar() {
        return repository.findAll();
    }

    public Optional<Projeto> buscar(Long id) {
        return repository.findById(id);
    }

    public Projeto criar(Projeto projeto) {
        return repository.save(projeto);
    }

    public Optional<Projeto> atualizar(Long id, Projeto projeto) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        projeto.setId(id);
        return Optional.of(repository.save(projeto));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
