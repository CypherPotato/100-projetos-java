package com.exemplo.estudiofotografia.repository;

import com.exemplo.estudiofotografia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
