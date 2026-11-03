package com.exemplo.transporteescolar.repository;

import com.exemplo.transporteescolar.model.Rota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RotaRepository extends JpaRepository<Rota, Long> {

    List<Rota> findByVeiculoId(Long veiculoId);
}
