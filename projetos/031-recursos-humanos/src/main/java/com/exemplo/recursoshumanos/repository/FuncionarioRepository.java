package com.exemplo.recursoshumanos.repository;

import com.exemplo.recursoshumanos.model.Funcionario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByDepartamentoId(Long departamentoId);
}
