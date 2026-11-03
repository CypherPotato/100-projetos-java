package com.exemplo.coralcomunitario.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "coristas")
public class Corista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private LocalDate dataEntrada;

    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "naipe_id")
    private Naipe naipe;
}
