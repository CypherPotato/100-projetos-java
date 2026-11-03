package com.exemplo.consorcio.repository;

import com.exemplo.consorcio.model.Cota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotaRepository extends JpaRepository<Cota, Long> {

    List<Cota> findByGrupoId(Long grupoId);
}
