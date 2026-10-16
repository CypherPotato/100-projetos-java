package com.exemplo.assistenciatecnica.repository;

import com.exemplo.assistenciatecnica.model.Aparelho;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AparelhoRepository extends JpaRepository<Aparelho, Long> {

    List<Aparelho> findByClienteId(Long clienteId);
}
