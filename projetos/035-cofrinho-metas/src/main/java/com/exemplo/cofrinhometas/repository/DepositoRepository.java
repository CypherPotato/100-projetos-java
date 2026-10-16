package com.exemplo.cofrinhometas.repository;

import com.exemplo.cofrinhometas.model.Deposito;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositoRepository extends JpaRepository<Deposito, Long> {

    List<Deposito> findByMetaId(Long metaId);
}
