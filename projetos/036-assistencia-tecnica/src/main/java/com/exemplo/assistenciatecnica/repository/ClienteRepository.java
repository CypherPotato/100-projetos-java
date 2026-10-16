package com.exemplo.assistenciatecnica.repository;

import com.exemplo.assistenciatecnica.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
