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
        this.descricao = dto.descricao();
        this.unidadeMedida = dto.unidadeMedida();
        this.valorMedida = dto.valorMedida();
        this.codigoBarra = dto.codigoBarra();
    }

    public void alterar(ProdutoReferenciaDTO dto){
        this.nome = dto.nome();
        this.urlImg = dto.urlImg();
        this.marca = dto.marca();
        this.descricao = dto.descricao();
        this.unidadeMedida = dto.unidadeMedida();
        this.valorMedida = dto.valorMedida();
        this.codigoBarra = dto.codigoBarra();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public void setValorMedida(Double valorMedida) {
        this.valorMedida = valorMedida;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
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
