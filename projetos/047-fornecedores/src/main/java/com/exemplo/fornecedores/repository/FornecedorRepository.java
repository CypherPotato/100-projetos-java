package com.exemplo.fornecedores.repository;

import com.exemplo.fornecedores.model.Fornecedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    List<Fornecedor> findByCategoriaId(Long categoriaId);
}
