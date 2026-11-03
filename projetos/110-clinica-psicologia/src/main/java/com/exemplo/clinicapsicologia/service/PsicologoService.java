package com.exemplo.clinicapsicologia.service;

import com.exemplo.clinicapsicologia.model.Psicologo;
import com.exemplo.clinicapsicologia.repository.PsicologoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PsicologoService {

    private final PsicologoRepository repository;

    public PsicologoService(PsicologoRepository repository) {
        this.repository = repository;
    }

    public List<Psicologo> listar() {
        return repository.findAll();
    }

    public Optional<Psicologo> buscar(Long id) {
        return repository.findById(id);
    }

    public Psicologo criar(Psicologo psicologo) {
        return repository.save(psicologo);
    }

    public Optional<Psicologo> atualizar(Long id, Psicologo psicologo) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        psicologo.setId(id);
        return Optional.of(repository.save(psicologo));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
