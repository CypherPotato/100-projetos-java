package com.exemplo.contasbancarias.repository;

import com.exemplo.contasbancarias.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<Banco, Long> {
}
