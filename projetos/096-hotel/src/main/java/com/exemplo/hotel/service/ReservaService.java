package com.exemplo.hotel.service;

import com.exemplo.hotel.model.Reserva;
import com.exemplo.hotel.repository.ReservaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    private final ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> listar(Long quartoId, Long hospedeId) {
        if (quartoId != null) {
            return repository.findByQuartoId(quartoId);
        }
        if (hospedeId != null) {
            return repository.findByHospedeId(hospedeId);
        }
        return repository.findAll();
    }

    public Optional<Reserva> buscar(Long id) {
        return repository.findById(id);
    }

    public Reserva criar(Reserva reserva) {
        return repository.save(reserva);
    }

    public Optional<Reserva> atualizar(Long id, Reserva reserva) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        reserva.setId(id);
        return Optional.of(repository.save(reserva));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
