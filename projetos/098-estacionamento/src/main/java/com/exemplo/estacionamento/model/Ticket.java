package com.exemplo.estacionamento.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;

    private LocalDateTime entrada;

    private LocalDateTime saida;

    private BigDecimal valorCobrado;

    @ManyToOne
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;
}
