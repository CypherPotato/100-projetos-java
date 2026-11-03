package com.exemplo.ateliecostura.repository;

import com.exemplo.ateliecostura.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
