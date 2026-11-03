package com.exemplo.featureflags.repository;

import com.exemplo.featureflags.model.Flag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlagRepository extends JpaRepository<Flag, Long> {

    List<Flag> findByAplicacaoId(Long aplicacaoId);
}
