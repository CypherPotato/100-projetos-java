package com.exemplo.pesqueiro.model;

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
@Table(name = "despescas")
public class Despesca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double pesoKg;

    private Integer quantidade;

    private LocalDateTime dataDespesca;

    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "tanque_id")
    private Tanque tanque;
}
