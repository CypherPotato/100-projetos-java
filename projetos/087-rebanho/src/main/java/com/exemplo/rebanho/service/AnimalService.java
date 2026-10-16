package com.exemplo.rebanho.service;

import com.exemplo.rebanho.model.Animal;
import com.exemplo.rebanho.repository.AnimalRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public List<Animal> listar(Long loteId) {
        if (loteId != null) {
            return repository.findByLoteId(loteId);
        }
        return repository.findAll();
    }

    public Optional<Animal> buscar(Long id) {
        return repository.findById(id);
    }

    public Animal criar(Animal animal) {
        return repository.save(animal);
    }

    public Optional<Animal> atualizar(Long id, Animal animal) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        animal.setId(id);
        return Optional.of(repository.save(animal));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
