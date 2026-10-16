package com.exemplo.cafeteria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "graos_cafe")
public class GraoCafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String variedade;

    private String origem;

    private BigDecimal precoKg;

    private Double estoqueKg;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariedade() {
        return variedade;
    }

    public void setVariedade(String variedade) {
        this.variedade = variedade;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public BigDecimal getPrecoKg() {
        return precoKg;
    }

    public void setPrecoKg(BigDecimal precoKg) {
        this.precoKg = precoKg;
    }

    public Double getEstoqueKg() {
        return estoqueKg;
    }

    public void setEstoqueKg(Double estoqueKg) {
        this.estoqueKg = estoqueKg;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}
