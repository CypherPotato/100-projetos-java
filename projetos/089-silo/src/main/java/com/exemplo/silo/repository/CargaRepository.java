package com.exemplo.silo.repository;

import com.exemplo.silo.model.Carga;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargaRepository extends JpaRepository<Carga, Long> {

    List<Carga> findBySiloId(Long siloId);
}
