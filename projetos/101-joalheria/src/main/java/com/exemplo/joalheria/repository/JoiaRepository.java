package com.exemplo.joalheria.repository;

import com.exemplo.joalheria.model.Joia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoiaRepository extends JpaRepository<Joia, Long> {

    List<Joia> findByCategoriaId(Long categoriaId);
}
