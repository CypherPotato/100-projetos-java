package com.exemplo.controlecontratos.service;

import com.exemplo.controlecontratos.model.Aditivo;
import com.exemplo.controlecontratos.repository.AditivoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AditivoService {

    private final AditivoRepository repository;

    public AditivoService(AditivoRepository repository) {
        this.repository = repository;
    }

    public List<Aditivo> listar(Long contratoId) {
        if (contratoId != null) {
            return repository.findByContratoId(contratoId);
        }
        return repository.findAll();
    }

    public Optional<Aditivo> buscar(Long id) {
        return repository.findById(id);
    }

    public Aditivo criar(Aditivo aditivo) {
        return repository.save(aditivo);
    }

    public Optional<Aditivo> atualizar(Long id, Aditivo aditivo) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        aditivo.setId(id);
        return Optional.of(repository.save(aditivo));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
