package com.exemplo.featureflags.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "flags")
public class Flag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Boolean ativa;

    private Integer percentualUsuarios;

    private LocalDateTime atualizadaEm;

    @ManyToOne
    @JoinColumn(name = "aplicacao_id")
    private Aplicacao aplicacao;
}
