package com.exemplo.coralcomunitario.repository;

import com.exemplo.coralcomunitario.model.Corista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoristaRepository extends JpaRepository<Corista, Long> {

    List<Corista> findByNaipeId(Long naipeId);
}
