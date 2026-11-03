package com.exemplo.bancoalimentos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "distribuicoes")
public class Distribuicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String familia;

    private LocalDate dataEntrega;

    private Double quantidadeKg;

    @ManyToOne
    @JoinColumn(name = "alimento_id")
    private Alimento alimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Double getQuantidadeKg() {
        return quantidadeKg;
    }

    public void setQuantidadeKg(Double quantidadeKg) {
        this.quantidadeKg = quantidadeKg;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }
}
