package com.exemplo.clinicamedica.service;

import com.exemplo.clinicamedica.model.Consulta;
import com.exemplo.clinicamedica.repository.ConsultaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public List<Consulta> listar(Long medicoId, Long pacienteId) {
        if (medicoId != null) {
            return repository.findByMedicoId(medicoId);
        }
        if (pacienteId != null) {
            return repository.findByPacienteId(pacienteId);
        }
        return repository.findAll();
    }

    public Optional<Consulta> buscar(Long id) {
        return repository.findById(id);
    }

    public Consulta criar(Consulta consulta) {
        return repository.save(consulta);
    }

    public Optional<Consulta> atualizar(Long id, Consulta consulta) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        consulta.setId(id);
        return Optional.of(repository.save(consulta));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
