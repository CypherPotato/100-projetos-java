package com.exemplo.gibiteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "revistas")
public class Revista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private Integer edicao;

    private Integer anoPublicacao;

    private Boolean emprestada;

    @ManyToOne
    @JoinColumn(name = "colecao_id")
    private Colecao colecao;
}
