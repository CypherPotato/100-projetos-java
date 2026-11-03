package com.exemplo.catalogoapis.repository;

import com.exemplo.catalogoapis.model.Endpoint;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndpointRepository extends JpaRepository<Endpoint, Long> {

    List<Endpoint> findByApiId(Long apiId);
}
