package com.exemplo.foodtruck.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "faturamentos")
public class Faturamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataDia;

    private BigDecimal totalVendas;

    private Integer clientesAtendidos;

    @ManyToOne
    @JoinColumn(name = "ponto_venda_id")
    private PontoVenda pontoVenda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataDia() {
        return dataDia;
    }

    public void setDataDia(LocalDate dataDia) {
        this.dataDia = dataDia;
    }

    public BigDecimal getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(BigDecimal totalVendas) {
        this.totalVendas = totalVendas;
    }

    public Integer getClientesAtendidos() {
        return clientesAtendidos;
    }

    public void setClientesAtendidos(Integer clientesAtendidos) {
        this.clientesAtendidos = clientesAtendidos;
    }

    public PontoVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }
}
