package com.exemplo.helpdesk.repository;

import com.exemplo.helpdesk.model.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitanteRepository extends JpaRepository<Solicitante, Long> {
}
