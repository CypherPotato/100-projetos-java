package com.exemplo.gestaoobras.repository;

import com.exemplo.gestaoobras.model.Material;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findByObraId(Long obraId);
}
