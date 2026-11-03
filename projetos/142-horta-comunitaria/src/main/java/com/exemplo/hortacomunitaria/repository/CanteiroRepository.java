package com.exemplo.hortacomunitaria.repository;

import com.exemplo.hortacomunitaria.model.Canteiro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanteiroRepository extends JpaRepository<Canteiro, Long> {

    List<Canteiro> findByMoradorId(Long moradorId);
}
