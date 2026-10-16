package com.exemplo.clubesocial.service;

import com.exemplo.clubesocial.model.Socio;
import com.exemplo.clubesocial.repository.SocioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SocioService {

    private final SocioRepository repository;

    public SocioService(SocioRepository repository) {
        this.repository = repository;
    }

    public List<Socio> listar(Long modalidadeId) {
        if (modalidadeId != null) {
            return repository.findByModalidadeId(modalidadeId);
        }
        return repository.findAll();
    }

    public Optional<Socio> buscar(Long id) {
        return repository.findById(id);
    }

    public Socio criar(Socio socio) {
        return repository.save(socio);
    }

    public Optional<Socio> atualizar(Long id, Socio socio) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        socio.setId(id);
        return Optional.of(repository.save(socio));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
