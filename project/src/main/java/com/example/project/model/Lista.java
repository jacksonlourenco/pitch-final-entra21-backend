package com.example.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lista")
public class Lista {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String dataCriacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Lista() {
    }
}
