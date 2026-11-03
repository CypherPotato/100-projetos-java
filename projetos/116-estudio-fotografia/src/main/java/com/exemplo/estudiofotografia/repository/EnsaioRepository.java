package com.exemplo.estudiofotografia.repository;

import com.exemplo.estudiofotografia.model.Ensaio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnsaioRepository extends JpaRepository<Ensaio, Long> {

    List<Ensaio> findByClienteId(Long clienteId);
}
