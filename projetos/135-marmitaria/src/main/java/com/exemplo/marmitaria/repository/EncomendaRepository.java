package com.exemplo.marmitaria.repository;

import com.exemplo.marmitaria.model.Encomenda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {

    List<Encomenda> findByMarmitaId(Long marmitaId);
}
