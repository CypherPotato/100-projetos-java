package com.exemplo.ouvidoria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "manifestacoes")
public class Manifestacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assunto;

    private String mensagem;

    private LocalDateTime dataRegistro;

    private Boolean resolvida;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;
}
