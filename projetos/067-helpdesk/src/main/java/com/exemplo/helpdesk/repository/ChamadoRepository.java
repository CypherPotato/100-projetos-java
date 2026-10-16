package com.exemplo.helpdesk.repository;

import com.exemplo.helpdesk.model.Chamado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findBySolicitanteId(Long solicitanteId);
}
