package com.exemplo.clinicamedica.service;

import com.exemplo.clinicamedica.model.Medico;
import com.exemplo.clinicamedica.repository.MedicoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    public List<Medico> listar() {
        return repository.findAll();
    }

    public Optional<Medico> buscar(Long id) {
        return repository.findById(id);
    }

    public Medico criar(Medico medico) {
        return repository.save(medico);
    }

    public Optional<Medico> atualizar(Long id, Medico medico) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        medico.setId(id);
        return Optional.of(repository.save(medico));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
