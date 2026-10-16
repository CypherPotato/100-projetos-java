package com.exemplo.cafeteria.repository;

import com.exemplo.cafeteria.model.GraoCafe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraoCafeRepository extends JpaRepository<GraoCafe, Long> {

    List<GraoCafe> findByFornecedorId(Long fornecedorId);
}
