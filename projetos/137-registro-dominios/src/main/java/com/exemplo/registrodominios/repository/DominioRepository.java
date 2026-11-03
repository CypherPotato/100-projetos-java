package com.exemplo.registrodominios.repository;

import com.exemplo.registrodominios.model.Dominio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DominioRepository extends JpaRepository<Dominio, Long> {

    List<Dominio> findByRegistradorId(Long registradorId);
}
