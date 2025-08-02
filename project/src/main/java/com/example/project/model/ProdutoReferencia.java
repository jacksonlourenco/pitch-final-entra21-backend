package com.example.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "produto_referencia")
public class ProdutoReferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String urlImg;
    private String descricao;
    private String unidadeMedida;
    private Double valorMedida;
    private String codigoBarra;

    public ProdutoReferencia() {
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public Double getValorMedida() {
        return valorMedida;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }
}
