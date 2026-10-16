package com.exemplo.igreja.model;

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
@Table(name = "membros")
public class Membro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private LocalDate dataBatismo;

    private BigDecimal contribuicaoMensal;

    @ManyToOne
    @JoinColumn(name = "celula_id")
    private Celula celula;
}
