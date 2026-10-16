package com.exemplo.assistenciatecnica.repository;

import com.exemplo.assistenciatecnica.model.Reparo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReparoRepository extends JpaRepository<Reparo, Long> {

    List<Reparo> findByAparelhoId(Long aparelhoId);
}
