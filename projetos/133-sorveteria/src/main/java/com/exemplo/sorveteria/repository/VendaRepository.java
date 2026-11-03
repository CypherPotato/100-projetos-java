package com.exemplo.sorveteria.repository;

import com.exemplo.sorveteria.model.Venda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findBySorveteId(Long sorveteId);
}
