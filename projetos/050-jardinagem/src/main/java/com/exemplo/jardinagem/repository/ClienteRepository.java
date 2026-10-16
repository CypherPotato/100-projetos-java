package com.exemplo.jardinagem.repository;

import com.exemplo.jardinagem.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
