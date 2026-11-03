package com.exemplo.empresamudancas.repository;

import com.exemplo.empresamudancas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
