package com.exemplo.padaria.model;

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
@Table(name = "fornadas")
public class Fornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String produto;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "padeiro_id")
    private Padeiro padeiro;
}
