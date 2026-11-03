package com.exemplo.protocolodocumentos.repository;

import com.exemplo.protocolodocumentos.model.Documento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findBySetorId(Long setorId);
}
