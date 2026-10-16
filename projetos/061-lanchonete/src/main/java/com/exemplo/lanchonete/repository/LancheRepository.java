package com.exemplo.lanchonete.repository;

import com.exemplo.lanchonete.model.Lanche;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancheRepository extends JpaRepository<Lanche, Long> {

    List<Lanche> findByCategoriaId(Long categoriaId);
}
