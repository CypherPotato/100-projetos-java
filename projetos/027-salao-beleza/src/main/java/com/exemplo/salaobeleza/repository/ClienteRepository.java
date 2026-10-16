package com.exemplo.salaobeleza.repository;

import com.exemplo.salaobeleza.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
