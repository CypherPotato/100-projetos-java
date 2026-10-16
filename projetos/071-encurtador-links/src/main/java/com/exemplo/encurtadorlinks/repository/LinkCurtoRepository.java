package com.exemplo.encurtadorlinks.repository;

import com.exemplo.encurtadorlinks.model.LinkCurto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkCurtoRepository extends JpaRepository<LinkCurto, Long> {

    List<LinkCurto> findByUsuarioId(Long usuarioId);
}
