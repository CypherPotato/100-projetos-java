package com.exemplo.hortifruti.repository;

import com.exemplo.hortifruti.model.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByProdutorId(Long produtorId);
}
