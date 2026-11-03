package com.exemplo.pesquisasatisfacao.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "respostas")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nota;

    private String comentario;

    private LocalDateTime respondidaEm;

    @ManyToOne
    @JoinColumn(name = "pesquisa_id")
    private Pesquisa pesquisa;
}
