package com.exemplo.transportadora.service;

import com.exemplo.transportadora.model.Motorista;
import com.exemplo.transportadora.repository.MotoristaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MotoristaService {

    private final MotoristaRepository repository;

    public MotoristaService(MotoristaRepository repository) {
        this.repository = repository;
    }

    public List<Motorista> listar() {
        return repository.findAll();
    }

    public Optional<Motorista> buscar(Long id) {
        return repository.findById(id);
    }

    public Motorista criar(Motorista motorista) {
        return repository.save(motorista);
    }

    public Optional<Motorista> atualizar(Long id, Motorista motorista) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        motorista.setId(id);
        return Optional.of(repository.save(motorista));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
