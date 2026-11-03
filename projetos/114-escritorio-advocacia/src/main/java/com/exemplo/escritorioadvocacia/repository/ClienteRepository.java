package com.exemplo.escritorioadvocacia.repository;

import com.exemplo.escritorioadvocacia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
