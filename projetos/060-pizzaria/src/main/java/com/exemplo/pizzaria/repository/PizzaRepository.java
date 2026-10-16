package com.exemplo.pizzaria.repository;

import com.exemplo.pizzaria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}
