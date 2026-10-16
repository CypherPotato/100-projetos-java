package com.exemplo.pizzaria.repository;

import com.exemplo.pizzaria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
