package com.exemplo.clinicaveterinaria.repository;

import com.exemplo.clinicaveterinaria.model.Vacina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {

    List<Vacina> findByAnimalId(Long animalId);
}
