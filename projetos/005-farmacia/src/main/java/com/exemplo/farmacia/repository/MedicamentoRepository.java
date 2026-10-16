package com.exemplo.farmacia.repository;

import com.exemplo.farmacia.model.Medicamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    List<Medicamento> findByFabricanteId(Long fabricanteId);
}
