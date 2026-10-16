package com.exemplo.patrimonio.repository;

import com.exemplo.patrimonio.model.Bem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BemRepository extends JpaRepository<Bem, Long> {

    List<Bem> findBySetorId(Long setorId);
}
