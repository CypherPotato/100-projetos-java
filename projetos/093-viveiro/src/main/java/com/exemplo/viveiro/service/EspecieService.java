package com.exemplo.viveiro.service;

import com.exemplo.viveiro.model.Especie;
import com.exemplo.viveiro.repository.EspecieRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EspecieService {

    private final EspecieRepository repository;

    public EspecieService(EspecieRepository repository) {
        this.repository = repository;
    }

    public List<Especie> listar() {
        return repository.findAll();
    }

    public Optional<Especie> buscar(Long id) {
        return repository.findById(id);
    }

    public Especie criar(Especie especie) {
        return repository.save(especie);
    }

    public Optional<Especie> atualizar(Long id, Especie especie) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        especie.setId(id);
        return Optional.of(repository.save(especie));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
