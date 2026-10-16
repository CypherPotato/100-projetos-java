package com.exemplo.adega.repository;

import com.exemplo.adega.model.Vinho;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VinhoRepository extends JpaRepository<Vinho, Long> {

    List<Vinho> findByVinicolaId(Long vinicolaId);
}
