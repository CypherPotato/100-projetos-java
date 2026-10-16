package com.exemplo.petshop.service;

import com.exemplo.petshop.model.Tutor;
import com.exemplo.petshop.repository.TutorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    private final TutorRepository repository;

    public TutorService(TutorRepository repository) {
        this.repository = repository;
    }

    public List<Tutor> listar() {
        return repository.findAll();
    }

    public Optional<Tutor> buscar(Long id) {
        return repository.findById(id);
    }

    public Tutor criar(Tutor tutor) {
        return repository.save(tutor);
    }

    public Optional<Tutor> atualizar(Long id, Tutor tutor) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        tutor.setId(id);
        return Optional.of(repository.save(tutor));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
