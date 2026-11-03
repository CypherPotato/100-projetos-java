package com.exemplo.galeriaarte.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "obras")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String tecnica;

    private BigDecimal preco;

    private Boolean vendida;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;
}
