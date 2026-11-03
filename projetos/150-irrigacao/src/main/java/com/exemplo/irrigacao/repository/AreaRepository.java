package com.exemplo.irrigacao.repository;

import com.exemplo.irrigacao.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
}
