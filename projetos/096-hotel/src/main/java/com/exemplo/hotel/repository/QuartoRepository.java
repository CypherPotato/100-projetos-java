package com.exemplo.hotel.repository;

import com.exemplo.hotel.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {
}
