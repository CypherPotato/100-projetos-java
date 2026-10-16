package com.exemplo.dedetizadora.model;

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
@Table(name = "aplicacoes")
public class Aplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String praga;

    private String produto;

    private BigDecimal valor;

    private LocalDate dataAplicacao;

    private Integer garantiaMeses;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
