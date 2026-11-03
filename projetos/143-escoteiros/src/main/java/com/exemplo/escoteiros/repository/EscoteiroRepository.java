package com.exemplo.escoteiros.repository;

import com.exemplo.escoteiros.model.Escoteiro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscoteiroRepository extends JpaRepository<Escoteiro, Long> {

    List<Escoteiro> findByPatrulhaId(Long patrulhaId);
}
