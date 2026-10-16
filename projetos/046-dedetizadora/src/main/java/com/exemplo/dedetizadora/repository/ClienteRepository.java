package com.exemplo.dedetizadora.repository;

import com.exemplo.dedetizadora.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
