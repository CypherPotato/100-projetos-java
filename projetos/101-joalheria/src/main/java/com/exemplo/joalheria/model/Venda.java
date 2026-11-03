package com.exemplo.joalheria.model;

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
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataVenda;

    private BigDecimal valorTotal;

    private String cliente;

    @ManyToOne
    @JoinColumn(name = "joia_id")
    private Joia joia;
}
