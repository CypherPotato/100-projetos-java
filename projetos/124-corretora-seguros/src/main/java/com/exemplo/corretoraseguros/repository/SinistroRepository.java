package com.exemplo.corretoraseguros.repository;

import com.exemplo.corretoraseguros.model.Sinistro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SinistroRepository extends JpaRepository<Sinistro, Long> {

    List<Sinistro> findByApoliceId(Long apoliceId);
}
