package com.exemplo.autoescola.repository;

import com.exemplo.autoescola.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
}
