package com.exemplo.lojaroupas.repository;

import com.exemplo.lojaroupas.model.Roupa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoupaRepository extends JpaRepository<Roupa, Long> {

    List<Roupa> findByCategoriaId(Long categoriaId);
}
