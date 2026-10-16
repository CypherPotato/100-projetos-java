package com.exemplo.lavanderia.model;

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
@Table(name = "pedidos_lavagem")
public class PedidoLavagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidadePecas;

    private BigDecimal valorTotal;

    private LocalDate dataRetirada;

    private Boolean entregue;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
