package com.exemplo.acougue.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cortes")
public class Corte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tipoCarne;

    private BigDecimal precoKg;

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

    public String getTipoCarne() {
        return tipoCarne;
    }

    public void setTipoCarne(String tipoCarne) {
        this.tipoCarne = tipoCarne;
    }

    public BigDecimal getPrecoKg() {
        return precoKg;
    }

    public void setPrecoKg(BigDecimal precoKg) {
        this.precoKg = precoKg;
    }
}
