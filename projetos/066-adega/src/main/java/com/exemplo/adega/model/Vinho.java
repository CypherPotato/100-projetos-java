package com.exemplo.adega.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vinhos")
public class Vinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rotulo;

    private Integer safra;

    private Double teorAlcoolico;

    private BigDecimal preco;

    private Integer estoque;

    @ManyToOne
    @JoinColumn(name = "vinicola_id")
    private Vinicola vinicola;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public Integer getSafra() {
        return safra;
    }

    public void setSafra(Integer safra) {
        this.safra = safra;
    }

    public Double getTeorAlcoolico() {
        return teorAlcoolico;
    }

    public void setTeorAlcoolico(Double teorAlcoolico) {
        this.teorAlcoolico = teorAlcoolico;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Vinicola getVinicola() {
        return vinicola;
    }

    public void setVinicola(Vinicola vinicola) {
        this.vinicola = vinicola;
    }
}
