package com.exemplo.estacionamento.repository;

import com.exemplo.estacionamento.model.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByVagaId(Long vagaId);
}
