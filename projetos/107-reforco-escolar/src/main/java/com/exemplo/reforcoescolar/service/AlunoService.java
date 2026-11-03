package com.exemplo.reforcoescolar.service;

import com.exemplo.reforcoescolar.model.Aluno;
import com.exemplo.reforcoescolar.repository.AlunoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<Aluno> listar() {
        return repository.findAll();
    }

    public Optional<Aluno> buscar(Long id) {
        return repository.findById(id);
    }

    public Aluno criar(Aluno aluno) {
        return repository.save(aluno);
    }

    public Optional<Aluno> atualizar(Long id, Aluno aluno) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        aluno.setId(id);
        return Optional.of(repository.save(aluno));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
