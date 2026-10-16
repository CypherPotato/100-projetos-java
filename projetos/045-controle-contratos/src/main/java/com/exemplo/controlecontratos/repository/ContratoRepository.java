package com.exemplo.controlecontratos.repository;

import com.exemplo.controlecontratos.model.Contrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    List<Contrato> findByEmpresaId(Long empresaId);
}
