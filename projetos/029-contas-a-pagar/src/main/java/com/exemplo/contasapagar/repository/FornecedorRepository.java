package com.exemplo.contasapagar.repository;

import com.exemplo.contasapagar.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
