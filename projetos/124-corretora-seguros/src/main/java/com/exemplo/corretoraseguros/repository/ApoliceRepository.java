package com.exemplo.corretoraseguros.repository;

import com.exemplo.corretoraseguros.model.Apolice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApoliceRepository extends JpaRepository<Apolice, Long> {

    List<Apolice> findBySeguradoId(Long seguradoId);
}
