package com.exemplo.hotel.repository;

import com.exemplo.hotel.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospedeRepository extends JpaRepository<Hospede, Long> {
}
