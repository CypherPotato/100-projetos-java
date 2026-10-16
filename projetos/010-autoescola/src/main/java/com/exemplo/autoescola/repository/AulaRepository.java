package com.exemplo.autoescola.repository;

import com.exemplo.autoescola.model.Aula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findByInstrutorId(Long instrutorId);

    List<Aula> findByVeiculoId(Long veiculoId);
}
