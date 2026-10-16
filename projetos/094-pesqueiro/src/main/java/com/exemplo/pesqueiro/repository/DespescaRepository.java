package com.exemplo.pesqueiro.repository;

import com.exemplo.pesqueiro.model.Despesca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespescaRepository extends JpaRepository<Despesca, Long> {

    List<Despesca> findByTanqueId(Long tanqueId);
}
