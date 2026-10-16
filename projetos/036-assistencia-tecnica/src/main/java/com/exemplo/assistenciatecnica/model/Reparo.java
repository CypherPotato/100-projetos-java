package com.exemplo.assistenciatecnica.model;

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
@Table(name = "reparos")
public class Reparo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String defeito;

    private BigDecimal valorOrcado;

    private LocalDate dataEntrada;

    private Boolean consertado;

    @ManyToOne
    @JoinColumn(name = "aparelho_id")
    private Aparelho aparelho;
}
