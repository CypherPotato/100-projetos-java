package com.exemplo.lavarapido.repository;

import com.exemplo.lavarapido.model.Lavagem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LavagemRepository extends JpaRepository<Lavagem, Long> {

    List<Lavagem> findByVeiculoId(Long veiculoId);
}
