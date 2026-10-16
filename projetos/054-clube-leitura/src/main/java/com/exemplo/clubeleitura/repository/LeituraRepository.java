package com.exemplo.clubeleitura.repository;

import com.exemplo.clubeleitura.model.Leitura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {

    List<Leitura> findByLivroId(Long livroId);

    List<Leitura> findByMembroId(Long membroId);
}
