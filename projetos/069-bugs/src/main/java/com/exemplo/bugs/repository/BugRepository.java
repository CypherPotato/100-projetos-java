package com.exemplo.bugs.repository;

import com.exemplo.bugs.model.Bug;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Long> {

    List<Bug> findByProjetoId(Long projetoId);
}
