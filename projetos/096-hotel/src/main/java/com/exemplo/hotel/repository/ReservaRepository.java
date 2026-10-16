package com.exemplo.hotel.repository;

import com.exemplo.hotel.model.Reserva;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByQuartoId(Long quartoId);

    List<Reserva> findByHospedeId(Long hospedeId);
}
