package com.exemplo.pizzaria.service;

import com.exemplo.pizzaria.model.Pizza;
import com.exemplo.pizzaria.repository.PizzaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    private final PizzaRepository repository;

    public PizzaService(PizzaRepository repository) {
        this.repository = repository;
    }

    public List<Pizza> listar() {
        return repository.findAll();
    }

    public Optional<Pizza> buscar(Long id) {
        return repository.findById(id);
    }

    public Pizza criar(Pizza pizza) {
        return repository.save(pizza);
    }

    public Optional<Pizza> atualizar(Long id, Pizza pizza) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        pizza.setId(id);
        return Optional.of(repository.save(pizza));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
