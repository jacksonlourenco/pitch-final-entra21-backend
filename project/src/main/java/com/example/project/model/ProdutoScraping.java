package com.example.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "produto_scraping")
public class ProdutoScraping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Double preco;
    private Double precoEspecial;
    private String dataScraping;
    private String urlImg;

    @ManyToOne
    @JoinColumn(name = "produto_referencia_id", nullable = false)
    private ProdutoReferencia produtoReferencia;

    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    public ProdutoScraping() {
    }
}
