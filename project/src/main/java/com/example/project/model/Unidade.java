package com.example.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "unidade")
public class Unidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String latitude;
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;

    public Unidade() {
    }
}
