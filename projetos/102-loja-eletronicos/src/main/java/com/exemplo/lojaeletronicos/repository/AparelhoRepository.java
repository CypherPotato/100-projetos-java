package com.exemplo.lojaeletronicos.repository;

import com.exemplo.lojaeletronicos.model.Aparelho;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AparelhoRepository extends JpaRepository<Aparelho, Long> {

    List<Aparelho> findByMarcaId(Long marcaId);
}
