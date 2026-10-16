package com.exemplo.petshop.repository;

import com.exemplo.petshop.model.Atendimento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByAnimalId(Long animalId);
}
