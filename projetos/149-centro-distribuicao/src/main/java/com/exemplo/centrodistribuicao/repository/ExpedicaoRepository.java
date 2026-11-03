package com.exemplo.centrodistribuicao.repository;

import com.exemplo.centrodistribuicao.model.Expedicao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpedicaoRepository extends JpaRepository<Expedicao, Long> {

    List<Expedicao> findByMercadoriaId(Long mercadoriaId);
}
