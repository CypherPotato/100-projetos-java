package com.exemplo.locadorajogos.repository;

import com.exemplo.locadorajogos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
