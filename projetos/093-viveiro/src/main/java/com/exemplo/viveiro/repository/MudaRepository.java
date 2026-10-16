package com.exemplo.viveiro.repository;

import com.exemplo.viveiro.model.Muda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MudaRepository extends JpaRepository<Muda, Long> {

    List<Muda> findByEspecieId(Long especieId);
}
