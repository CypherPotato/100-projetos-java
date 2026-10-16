package com.exemplo.adega.service;

import com.exemplo.adega.model.Vinicola;
import com.exemplo.adega.repository.VinicolaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class VinicolaService {

    private final VinicolaRepository repository;

    public VinicolaService(VinicolaRepository repository) {
        this.repository = repository;
    }

    public List<Vinicola> listar() {
        return repository.findAll();
    }

    public Optional<Vinicola> buscar(Long id) {
        return repository.findById(id);
    }

    public Vinicola criar(Vinicola vinicola) {
        return repository.save(vinicola);
    }

    public Optional<Vinicola> atualizar(Long id, Vinicola vinicola) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        vinicola.setId(id);
        return Optional.of(repository.save(vinicola));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
