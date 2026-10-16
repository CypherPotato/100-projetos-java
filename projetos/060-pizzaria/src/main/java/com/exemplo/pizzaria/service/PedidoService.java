package com.exemplo.pizzaria.service;

import com.exemplo.pizzaria.model.Pedido;
import com.exemplo.pizzaria.repository.PedidoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> listar(Long clienteId, Long pizzaId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        if (pizzaId != null) {
            return repository.findByPizzaId(pizzaId);
        }
        return repository.findAll();
    }

    public Optional<Pedido> buscar(Long id) {
        return repository.findById(id);
    }

    public Pedido criar(Pedido pedido) {
        return repository.save(pedido);
    }

    public Optional<Pedido> atualizar(Long id, Pedido pedido) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        pedido.setId(id);
        return Optional.of(repository.save(pedido));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
