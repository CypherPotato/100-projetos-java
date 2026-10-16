package com.exemplo.contasbancarias.repository;

import com.exemplo.contasbancarias.model.ContaBancaria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {

    List<ContaBancaria> findByBancoId(Long bancoId);
}
