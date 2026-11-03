package com.exemplo.boliche.repository;

import com.exemplo.boliche.model.Reserva;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByPistaId(Long pistaId);
}
