package com.exemplo.granjaavicola.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataAlojamento;

    private Integer quantidadeAves;

    private String raca;

    @ManyToOne
    @JoinColumn(name = "galpao_id")
    private Galpao galpao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAlojamento() {
        return dataAlojamento;
    }

    public void setDataAlojamento(LocalDate dataAlojamento) {
        this.dataAlojamento = dataAlojamento;
    }

    public Integer getQuantidadeAves() {
        return quantidadeAves;
    }

    public void setQuantidadeAves(Integer quantidadeAves) {
        this.quantidadeAves = quantidadeAves;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Galpao getGalpao() {
        return galpao;
    }

    public void setGalpao(Galpao galpao) {
        this.galpao = galpao;
    }
}
