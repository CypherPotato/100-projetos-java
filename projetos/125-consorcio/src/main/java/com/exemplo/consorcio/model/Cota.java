package com.exemplo.consorcio.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cotas")
public class Cota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String participante;

    private Boolean contemplada;

    private BigDecimal parcela;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
}
