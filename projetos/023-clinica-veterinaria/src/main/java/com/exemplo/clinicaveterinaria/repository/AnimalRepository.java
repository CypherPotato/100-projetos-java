package com.exemplo.clinicaveterinaria.repository;

import com.exemplo.clinicaveterinaria.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
