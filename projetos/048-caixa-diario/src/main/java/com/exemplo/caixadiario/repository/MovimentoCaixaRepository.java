package com.exemplo.caixadiario.repository;

import com.exemplo.caixadiario.model.MovimentoCaixa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoCaixaRepository extends JpaRepository<MovimentoCaixa, Long> {

    List<MovimentoCaixa> findByCaixaId(Long caixaId);
}
