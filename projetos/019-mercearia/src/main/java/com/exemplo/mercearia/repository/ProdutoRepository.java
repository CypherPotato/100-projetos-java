package com.exemplo.mercearia.repository;

import com.exemplo.mercearia.model.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByFornecedorId(Long fornecedorId);
}
