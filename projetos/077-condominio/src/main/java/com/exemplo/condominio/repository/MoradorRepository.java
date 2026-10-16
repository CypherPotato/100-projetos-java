package com.exemplo.condominio.repository;

import com.exemplo.condominio.model.Morador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoradorRepository extends JpaRepository<Morador, Long> {

    List<Morador> findByApartamentoId(Long apartamentoId);
}
