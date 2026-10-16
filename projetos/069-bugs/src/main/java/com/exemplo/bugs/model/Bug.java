package com.exemplo.bugs.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bugs")
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String severidade;

    private String situacao;

    private LocalDateTime registradoEm;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;
}
