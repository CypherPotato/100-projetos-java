package com.exemplo.agenciaviagens.service;

import com.exemplo.agenciaviagens.model.Cliente;
import com.exemplo.agenciaviagens.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Optional<Cliente> buscar(Long id) {
        return repository.findById(id);
    }

    public Cliente criar(Cliente cliente) {
        return repository.save(cliente);
    }

    public Optional<Cliente> atualizar(Long id, Cliente cliente) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        cliente.setId(id);
        return Optional.of(repository.save(cliente));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
