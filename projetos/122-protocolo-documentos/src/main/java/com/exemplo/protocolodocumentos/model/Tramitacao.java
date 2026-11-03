package com.exemplo.protocolodocumentos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tramitacoes")
public class Tramitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private String despacho;

    private Boolean urgente;

    @ManyToOne
    @JoinColumn(name = "documento_id")
    private Documento documento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDespacho() {
        return despacho;
    }

    public void setDespacho(String despacho) {
        this.despacho = despacho;
    }

    public Boolean getUrgente() {
        return urgente;
    }

    public void setUrgente(Boolean urgente) {
        this.urgente = urgente;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
}
