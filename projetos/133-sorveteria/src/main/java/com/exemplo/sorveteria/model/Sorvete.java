package com.exemplo.sorveteria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sorvetes")
public class Sorvete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tamanho;

    private BigDecimal preco;

    private String cobertura;

    @ManyToOne
    @JoinColumn(name = "sabor_id")
    private Sabor sabor;
}
