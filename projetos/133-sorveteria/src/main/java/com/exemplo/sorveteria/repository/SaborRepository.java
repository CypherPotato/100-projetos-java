package com.exemplo.sorveteria.repository;

import com.exemplo.sorveteria.model.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaborRepository extends JpaRepository<Sabor, Long> {
}
