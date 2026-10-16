package com.exemplo.hospital.repository;

import com.exemplo.hospital.model.Internacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternacaoRepository extends JpaRepository<Internacao, Long> {

    List<Internacao> findByLeitoId(Long leitoId);
}
