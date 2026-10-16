package com.exemplo.controlecontratos.repository;

import com.exemplo.controlecontratos.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
