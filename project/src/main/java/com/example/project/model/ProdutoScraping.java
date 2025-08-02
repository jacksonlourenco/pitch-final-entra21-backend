package com.example.project.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "produto_scraping")
public class ProdutoScraping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Double preco;
    private Double precoEspecial;
    private LocalDateTime dataScraping;
    private String urlImg;

    @ManyToOne
    @JoinColumn(name = "produto_referencia_id", nullable = false)
    private ProdutoReferencia produtoReferencia;

    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    public ProdutoScraping() {
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public Double getPrecoEspecial() {
        return precoEspecial;
    }

    public LocalDateTime getDataScraping() {
        return dataScraping;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public ProdutoReferencia getProdutoReferencia() {
        return produtoReferencia;
    }

    public Unidade getUnidade() {
        return unidade;
    }
}
