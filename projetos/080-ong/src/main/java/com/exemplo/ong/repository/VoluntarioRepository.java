package com.exemplo.ong.repository;

import com.exemplo.ong.model.Voluntario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {

    List<Voluntario> findByAcaoId(Long acaoId);
}
