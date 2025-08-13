package com.checkbuy.project.domain.model;

import com.checkbuy.project.domain.dto.ProdutoReferenciaDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "produto_referencia")
public class ProdutoReferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String urlImg;
    private String marca;
    private String descricao;
    private String unidadeMedida;
    private Double valorMedida;
    private String codigoBarra;

    //JPA - CONSTRUTOR PADRÃO VAZIO
    public ProdutoReferencia() {}

    public ProdutoReferencia(ProdutoReferenciaDTO dto) {
        this.nome = dto.nome();
        this.urlImg = dto.urlImg();
        this.marca = dto.marca();
        this.descricao = dto.marca();
        this.unidadeMedida = dto.unidadeMedida();
        this.valorMedida = dto.valorMedida();
        this.codigoBarra = dto.codigoBarra();
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

    public String getMarca() {
        return marca;
    }
}
