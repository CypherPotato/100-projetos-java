package com.exemplo.controleestoque.repository;

import com.exemplo.controleestoque.model.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
}
