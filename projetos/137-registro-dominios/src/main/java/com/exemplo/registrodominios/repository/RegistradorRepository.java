package com.exemplo.registrodominios.repository;

import com.exemplo.registrodominios.model.Registrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistradorRepository extends JpaRepository<Registrador, Long> {
}
