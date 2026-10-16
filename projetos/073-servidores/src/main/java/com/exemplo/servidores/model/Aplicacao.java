package com.exemplo.servidores.model;

import jakarta.persistence.*;
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

    private String nome;

    private String versao;

    private Integer porta;

    private Boolean emExecucao;

    @ManyToOne
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;
}
