package com.exemplo.campeonato.repository;

import com.exemplo.campeonato.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Long> {
}
