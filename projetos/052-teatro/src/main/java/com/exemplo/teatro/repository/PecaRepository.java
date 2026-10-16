package com.exemplo.teatro.repository;

import com.exemplo.teatro.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PecaRepository extends JpaRepository<Peca, Long> {
}
