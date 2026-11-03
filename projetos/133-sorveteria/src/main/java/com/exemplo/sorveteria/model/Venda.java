package com.exemplo.sorveteria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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

    private LocalDateTime dataHora;

    private Integer quantidade;

    private String formaPagamento;

    @ManyToOne
    @JoinColumn(name = "sorvete_id")
    private Sorvete sorvete;
}
