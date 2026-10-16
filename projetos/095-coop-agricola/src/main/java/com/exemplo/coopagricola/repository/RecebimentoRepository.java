package com.exemplo.coopagricola.repository;

import com.exemplo.coopagricola.model.Recebimento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecebimentoRepository extends JpaRepository<Recebimento, Long> {

    List<Recebimento> findByAgricultorId(Long agricultorId);
}
