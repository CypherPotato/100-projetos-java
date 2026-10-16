package com.exemplo.padaria.repository;

import com.exemplo.padaria.model.Padeiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PadeiroRepository extends JpaRepository<Padeiro, Long> {
}
