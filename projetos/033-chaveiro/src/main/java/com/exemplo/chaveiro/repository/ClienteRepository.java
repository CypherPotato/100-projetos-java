package com.exemplo.chaveiro.repository;

import com.exemplo.chaveiro.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
