package com.exemplo.servidores.repository;

import com.exemplo.servidores.model.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {
}
