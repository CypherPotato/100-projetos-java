package com.exemplo.colecaofilmes.repository;

import com.exemplo.colecaofilmes.model.Filme;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

    List<Filme> findByDiretorId(Long diretorId);
}
