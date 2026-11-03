package com.exemplo.lojaesportes.repository;

import com.exemplo.lojaesportes.model.Artigo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> {

    List<Artigo> findByModalidadeId(Long modalidadeId);
}
