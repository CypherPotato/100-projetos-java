package com.exemplo.apikeys.repository;

import com.exemplo.apikeys.model.ApiKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    List<ApiKey> findByClienteId(Long clienteId);
}
