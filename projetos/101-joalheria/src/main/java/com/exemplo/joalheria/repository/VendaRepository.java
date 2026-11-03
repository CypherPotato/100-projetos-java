package com.exemplo.joalheria.repository;

import com.exemplo.joalheria.model.Venda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByJoiaId(Long joiaId);
}
