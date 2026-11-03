package com.exemplo.clinicapsicologia.service;

import com.exemplo.clinicapsicologia.model.Paciente;
import com.exemplo.clinicapsicologia.repository.PacienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<Paciente> listar() {
        return repository.findAll();
    }

    public Optional<Paciente> buscar(Long id) {
        return repository.findById(id);
    }

    public Paciente criar(Paciente paciente) {
        return repository.save(paciente);
    }

    public Optional<Paciente> atualizar(Long id, Paciente paciente) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        paciente.setId(id);
        return Optional.of(repository.save(paciente));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
