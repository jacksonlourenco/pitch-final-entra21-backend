package com.checkbuy.project.domain.listaresultado.model;

import com.checkbuy.project.domain.lista.model.Lista;
import com.checkbuy.project.domain.produtoreferencia.model.ProdutoReferencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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

}
