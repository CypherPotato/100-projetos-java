package com.exemplo.lavanderia.service;

import com.exemplo.lavanderia.model.PedidoLavagem;
import com.exemplo.lavanderia.repository.PedidoLavagemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PedidoLavagemService {

    private final PedidoLavagemRepository repository;

    public PedidoLavagemService(PedidoLavagemRepository repository) {
        this.repository = repository;
    }

    public List<PedidoLavagem> listar(Long clienteId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    public Optional<PedidoLavagem> buscar(Long id) {
        return repository.findById(id);
    }

    public PedidoLavagem criar(PedidoLavagem pedidoLavagem) {
        return repository.save(pedidoLavagem);
    }

    public Optional<PedidoLavagem> atualizar(Long id, PedidoLavagem pedidoLavagem) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        pedidoLavagem.setId(id);
        return Optional.of(repository.save(pedidoLavagem));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
