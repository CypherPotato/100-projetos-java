package com.exemplo.empresamudancas.repository;

import com.exemplo.empresamudancas.model.Mudanca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MudancaRepository extends JpaRepository<Mudanca, Long> {

    List<Mudanca> findByClienteId(Long clienteId);

    List<Mudanca> findByCaminhaoId(Long caminhaoId);
}
