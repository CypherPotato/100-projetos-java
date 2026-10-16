package com.exemplo.encanador.repository;

import com.exemplo.encanador.model.VisitaTecnica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitaTecnicaRepository extends JpaRepository<VisitaTecnica, Long> {

    List<VisitaTecnica> findByClienteId(Long clienteId);
}
