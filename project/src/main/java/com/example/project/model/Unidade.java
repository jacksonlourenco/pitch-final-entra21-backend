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
    private String municipio;
    private String estado;
    private String cep;
    private String latitude;
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;

    public Unidade() {
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getBairro() {
        return bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }
}
