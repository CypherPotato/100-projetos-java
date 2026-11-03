package com.exemplo.cafeicultura.model;

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

    private LocalDate dataColheita;

    private Integer sacas;

    private BigDecimal precoSaca;

    @ManyToOne
    @JoinColumn(name = "talhao_id")
    private Talhao talhao;
}
