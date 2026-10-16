package com.exemplo.cartoescredito.repository;

import com.exemplo.cartoescredito.model.Compra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByFaturaId(Long faturaId);
}
