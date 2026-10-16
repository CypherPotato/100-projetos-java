package com.exemplo.contasapagar.repository;

import com.exemplo.contasapagar.model.ContaPagar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {

    List<ContaPagar> findByFornecedorId(Long fornecedorId);
}
