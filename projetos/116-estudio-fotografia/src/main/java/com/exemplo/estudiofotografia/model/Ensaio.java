package com.exemplo.estudiofotografia.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ensaios")
public class Ensaio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tema;

    private LocalDateTime dataEnsaio;

    private Integer quantidadeFotos;

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public LocalDateTime getDataEnsaio() {
        return dataEnsaio;
    }

    public void setDataEnsaio(LocalDateTime dataEnsaio) {
        this.dataEnsaio = dataEnsaio;
    }

    public Integer getQuantidadeFotos() {
        return quantidadeFotos;
    }

    public void setQuantidadeFotos(Integer quantidadeFotos) {
        this.quantidadeFotos = quantidadeFotos;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
