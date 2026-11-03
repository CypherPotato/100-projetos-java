package com.exemplo.gibiteca.repository;

import com.exemplo.gibiteca.model.Revista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevistaRepository extends JpaRepository<Revista, Long> {

    List<Revista> findByColecaoId(Long colecaoId);
}
