package com.exemplo.graficarapida.repository;

import com.exemplo.graficarapida.model.Impressao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpressaoRepository extends JpaRepository<Impressao, Long> {

    List<Impressao> findByClienteId(Long clienteId);
}
