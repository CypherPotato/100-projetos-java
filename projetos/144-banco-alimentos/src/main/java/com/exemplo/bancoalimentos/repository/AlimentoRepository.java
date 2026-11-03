package com.exemplo.bancoalimentos.repository;

import com.exemplo.bancoalimentos.model.Alimento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {

    List<Alimento> findByDoadorId(Long doadorId);
}
