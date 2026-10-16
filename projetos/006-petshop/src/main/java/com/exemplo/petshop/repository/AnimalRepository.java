package com.exemplo.petshop.repository;

import com.exemplo.petshop.model.Animal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByTutorId(Long tutorId);
}
