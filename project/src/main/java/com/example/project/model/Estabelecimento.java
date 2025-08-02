package com.example.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estabelecimento")
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;

    public Estabelecimento() {
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
