package com.exemplo.laticinio.repository;

import com.exemplo.laticinio.model.Coleta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColetaRepository extends JpaRepository<Coleta, Long> {

    List<Coleta> findByProdutorId(Long produtorId);
}
