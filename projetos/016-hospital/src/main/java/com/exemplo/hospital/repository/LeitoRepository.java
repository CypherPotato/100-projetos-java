package com.exemplo.hospital.repository;

import com.exemplo.hospital.model.Leito;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeitoRepository extends JpaRepository<Leito, Long> {

    List<Leito> findByAlaId(Long alaId);
}
