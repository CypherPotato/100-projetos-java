package com.exemplo.graficarapida.repository;

import com.exemplo.graficarapida.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
