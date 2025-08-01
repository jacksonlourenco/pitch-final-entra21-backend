package com.example.project.model;

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


    private int quantidade;

    public ListaResultado() {
    }
}
