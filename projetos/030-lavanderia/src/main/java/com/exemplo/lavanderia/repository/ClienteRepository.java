package com.exemplo.lavanderia.repository;

import com.exemplo.lavanderia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
