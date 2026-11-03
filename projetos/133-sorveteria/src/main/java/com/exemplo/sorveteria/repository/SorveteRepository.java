package com.exemplo.sorveteria.repository;

import com.exemplo.sorveteria.model.Sorvete;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SorveteRepository extends JpaRepository<Sorvete, Long> {

    List<Sorvete> findBySaborId(Long saborId);
}
