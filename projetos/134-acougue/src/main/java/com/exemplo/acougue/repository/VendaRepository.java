package com.exemplo.acougue.repository;

import com.exemplo.acougue.model.Venda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByCorteId(Long corteId);
}
