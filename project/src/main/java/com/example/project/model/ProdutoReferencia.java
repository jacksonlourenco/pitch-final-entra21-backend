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
    private String valorMedida;
    private String codigoBarra;

    public ProdutoReferencia() {
    }
}
