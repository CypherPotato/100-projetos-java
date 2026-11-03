package com.exemplo.registrodominios.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dominios")
public class Dominio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataExpiracao;

    private Boolean renovacaoAutomatica;

    private BigDecimal custoAnual;

    @ManyToOne
    @JoinColumn(name = "registrador_id")
    private Registrador registrador;
}
