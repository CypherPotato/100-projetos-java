package com.exemplo.rebanho.repository;

import com.exemplo.rebanho.model.Animal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByLoteId(Long loteId);
}
