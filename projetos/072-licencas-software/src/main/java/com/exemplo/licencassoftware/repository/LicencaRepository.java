package com.exemplo.licencassoftware.repository;

import com.exemplo.licencassoftware.model.Licenca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicencaRepository extends JpaRepository<Licenca, Long> {

    List<Licenca> findBySoftwareId(Long softwareId);
}
