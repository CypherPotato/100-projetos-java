package com.exemplo.catalogoapis.repository;

import com.exemplo.catalogoapis.model.Api;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository<Api, Long> {

    List<Api> findByEquipeId(Long equipeId);
}
