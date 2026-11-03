package com.exemplo.hortacomunitaria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "canteiros")
public class Canteiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    private Double areaM2;

    private Boolean irrigado;

    @ManyToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(Double areaM2) {
        this.areaM2 = areaM2;
    }

    public Boolean getIrrigado() {
        return irrigado;
    }

    public void setIrrigado(Boolean irrigado) {
        this.irrigado = irrigado;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }
}
