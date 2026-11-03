package com.exemplo.lojaeletronicos.repository;

import com.exemplo.lojaeletronicos.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
