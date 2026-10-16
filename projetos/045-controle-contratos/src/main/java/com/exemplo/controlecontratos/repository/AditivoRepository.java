package com.exemplo.controlecontratos.repository;

import com.exemplo.controlecontratos.model.Aditivo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AditivoRepository extends JpaRepository<Aditivo, Long> {

    List<Aditivo> findByContratoId(Long contratoId);
}
