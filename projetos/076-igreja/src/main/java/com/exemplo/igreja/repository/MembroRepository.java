package com.exemplo.igreja.repository;

import com.exemplo.igreja.model.Membro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroRepository extends JpaRepository<Membro, Long> {

    List<Membro> findByCelulaId(Long celulaId);
}
