package com.exemplo.centrodistribuicao.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expedicoes")
public class Expedicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataSaida;

    private String destino;

    private Boolean entregue;

    @ManyToOne
    @JoinColumn(name = "mercadoria_id")
    private Mercadoria mercadoria;
}
