package com.example.project.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_resultado")
public class ListaResultado {
    @EmbeddedId
    private ListaResultadoId id;

    @ManyToOne
    @MapsId("listaId")
    @JoinColumn(name = "lista_id")
    private Lista lista;

    @ManyToOne
    @MapsId("produtoReferenciaId")
    @JoinColumn(name = "produto_referencia_id")
    private ProdutoReferencia produtoReferencia;


    private Integer quantidade;

    public ListaResultado() {
    }

    public ListaResultadoId getId() {
        return id;
    }

    public Lista getLista() {
        return lista;
    }

    public ProdutoReferencia getProdutoReferencia() {
        return produtoReferencia;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
