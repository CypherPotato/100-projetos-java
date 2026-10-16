package com.exemplo.apikeys.repository;

import com.exemplo.apikeys.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
