package com.exemplo.creche.repository;

import com.exemplo.creche.model.Crianca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriancaRepository extends JpaRepository<Crianca, Long> {

    List<Crianca> findByResponsavelId(Long responsavelId);
}
