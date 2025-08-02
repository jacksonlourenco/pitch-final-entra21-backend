package com.example.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String nome;

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }
}
