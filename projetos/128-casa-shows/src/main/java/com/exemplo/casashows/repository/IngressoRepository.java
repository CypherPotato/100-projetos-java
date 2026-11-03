package com.exemplo.casashows.repository;

import com.exemplo.casashows.model.Ingresso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

    List<Ingresso> findByShowId(Long showId);
}
