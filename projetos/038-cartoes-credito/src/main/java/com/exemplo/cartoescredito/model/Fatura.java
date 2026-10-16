package com.exemplo.cartoescredito.model;

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
@Table(name = "faturas")
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String competencia;

    private BigDecimal valorTotal;

    private LocalDate vencimento;

    private Boolean paga;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
}
