package com.exemplo.transporteescolar.repository;

import com.exemplo.transporteescolar.model.Passageiro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassageiroRepository extends JpaRepository<Passageiro, Long> {

    List<Passageiro> findByRotaId(Long rotaId);
}
