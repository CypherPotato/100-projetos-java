package com.exemplo.apikeys.repository;

import com.exemplo.apikeys.model.Consumo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumoRepository extends JpaRepository<Consumo, Long> {

    List<Consumo> findByApiKeyId(Long apiKeyId);
}
