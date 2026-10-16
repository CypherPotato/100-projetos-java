package com.exemplo.apicultura.model;

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
@Table(name = "colheitas")
public class Colheita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double quantidadeKg;

    private LocalDate dataColheita;

    private BigDecimal valorEstimado;

    @ManyToOne
    @JoinColumn(name = "colmeia_id")
    private Colmeia colmeia;
}
