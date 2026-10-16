package com.exemplo.papelaria.repository;

import com.exemplo.papelaria.model.Venda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByProdutoId(Long produtoId);
}
