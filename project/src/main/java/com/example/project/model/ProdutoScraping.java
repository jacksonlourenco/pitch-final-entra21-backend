package com.example.project.model;

import com.example.project.service.dto.supemercado.cooper.ApiResponse;
import com.example.project.service.dto.supemercado.cooper.Variant;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "produto_referencia_id", nullable = true)
    private ProdutoReferencia produtoReferencia;

    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    public ProdutoScraping() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setPrecoEspecial(Double precoEspecial) {
        this.precoEspecial = precoEspecial;
    }

    public void setDataScraping(LocalDateTime dataScraping) {
        this.dataScraping = dataScraping;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public void setProdutoReferencia(ProdutoReferencia produtoReferencia) {
        this.produtoReferencia = produtoReferencia;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
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
