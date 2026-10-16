package com.exemplo.clubesocial.repository;

import com.exemplo.clubesocial.model.Socio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocioRepository extends JpaRepository<Socio, Long> {

    List<Socio> findByModalidadeId(Long modalidadeId);
}
