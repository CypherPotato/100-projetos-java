package com.exemplo.ateliecostura.model;

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
@Table(name = "encomendas")
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String peca;

    private LocalDate prazoEntrega;

    private BigDecimal valor;

    private Boolean entregue;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
