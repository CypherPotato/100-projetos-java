package com.exemplo.agenciaviagens.repository;

import com.exemplo.agenciaviagens.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
