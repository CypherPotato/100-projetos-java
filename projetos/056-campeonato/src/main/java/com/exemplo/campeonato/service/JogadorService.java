package com.exemplo.campeonato.service;

import com.exemplo.campeonato.model.Jogador;
import com.exemplo.campeonato.repository.JogadorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class JogadorService {

    private final JogadorRepository repository;

    public JogadorService(JogadorRepository repository) {
        this.repository = repository;
    }

    public List<Jogador> listar(Long equipeId) {
        if (equipeId != null) {
            return repository.findByEquipeId(equipeId);
        }
        return repository.findAll();
    }

    public Optional<Jogador> buscar(Long id) {
        return repository.findById(id);
    }

    public Jogador criar(Jogador jogador) {
        return repository.save(jogador);
    }

    public Optional<Jogador> atualizar(Long id, Jogador jogador) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        jogador.setId(id);
        return Optional.of(repository.save(jogador));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
