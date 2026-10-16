package com.exemplo.playlist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private Integer duracaoSegundos;

    private Integer anoLancamento;

    private Boolean favorita;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;
}
