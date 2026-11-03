package com.exemplo.casashows.repository;

import com.exemplo.casashows.model.Show;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByArtistaId(Long artistaId);
}
