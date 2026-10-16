package com.exemplo.licencassoftware.repository;

import com.exemplo.licencassoftware.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
}
