package com.exemplo.laboratorioexames.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exames")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataColeta;

    private Boolean resultadoLiberado;

    @ManyToOne
    @JoinColumn(name = "tipo_exame_id")
    private TipoExame tipoExame;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}
