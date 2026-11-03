package com.exemplo.bancoalimentos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "alimentos")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate validade;

    private Double quantidadeKg;

    @ManyToOne
    @JoinColumn(name = "doador_id")
    private Doador doador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public Double getQuantidadeKg() {
        return quantidadeKg;
    }

    public void setQuantidadeKg(Double quantidadeKg) {
        this.quantidadeKg = quantidadeKg;
    }

    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }
}
