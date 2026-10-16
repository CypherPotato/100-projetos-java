package com.exemplo.petshop.service;

import com.exemplo.petshop.model.Animal;
import com.exemplo.petshop.repository.AnimalRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public List<Animal> listar(Long tutorId) {
        if (tutorId != null) {
            return repository.findByTutorId(tutorId);
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
