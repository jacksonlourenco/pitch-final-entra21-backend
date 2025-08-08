package com.checkbuy.project.domain.model.alias;

import com.checkbuy.project.domain.model.ProdutoReferencia;
import com.checkbuy.project.domain.model.Unidade;
import jakarta.persistence.*;

@Entity
@Table(name = "alias_produto_referencia")
public class AliasProdutoReferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String alias;

    @ManyToOne
    @JoinColumn(name = "produto_referencia_id", nullable = false)
    private ProdutoReferencia produtoReferencia;

    public AliasProdutoReferencia() {
    }

    public AliasProdutoReferencia(String presentation, Unidade unidade) {
        this.alias = presentation;
       sdfsdfsdsdfsfd
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ProdutoReferencia getProdutoReferencia() {
        return produtoReferencia;
    }

    public void setProdutoReferencia(ProdutoReferencia produtoReferencia) {
        this.produtoReferencia = produtoReferencia;
    }
}
