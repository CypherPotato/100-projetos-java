package com.exemplo.jardinagem.repository;

import com.exemplo.jardinagem.model.ServicoJardim;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoJardimRepository extends JpaRepository<ServicoJardim, Long> {

    List<ServicoJardim> findByClienteId(Long clienteId);
}
