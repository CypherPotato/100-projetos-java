package com.exemplo.floricultura.repository;

import com.exemplo.floricultura.model.Encomenda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {

    List<Encomenda> findByClienteId(Long clienteId);
}
