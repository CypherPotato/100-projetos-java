package com.exemplo.repositoriosnippets.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "snippets")
public class Snippet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String codigo;

    private LocalDateTime criadoEm;

    @ManyToOne
    @JoinColumn(name = "linguagem_id")
    private Linguagem linguagem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Linguagem getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(Linguagem linguagem) {
        this.linguagem = linguagem;
    }
}
