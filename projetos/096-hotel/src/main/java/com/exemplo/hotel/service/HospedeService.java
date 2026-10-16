package com.exemplo.hotel.service;

import com.exemplo.hotel.model.Hospede;
import com.exemplo.hotel.repository.HospedeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class HospedeService {

    private final HospedeRepository repository;

    public HospedeService(HospedeRepository repository) {
        this.repository = repository;
    }

    public List<Hospede> listar() {
        return repository.findAll();
    }

    public Optional<Hospede> buscar(Long id) {
        return repository.findById(id);
    }

    public Hospede criar(Hospede hospede) {
        return repository.save(hospede);
    }

    public Optional<Hospede> atualizar(Long id, Hospede hospede) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        hospede.setId(id);
        return Optional.of(repository.save(hospede));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
