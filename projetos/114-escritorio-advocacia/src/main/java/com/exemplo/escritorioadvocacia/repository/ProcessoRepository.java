package com.exemplo.escritorioadvocacia.repository;

import com.exemplo.escritorioadvocacia.model.Processo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    List<Processo> findByClienteId(Long clienteId);
}
