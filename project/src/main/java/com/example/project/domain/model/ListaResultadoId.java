package com.example.project.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListaResultadoId implements Serializable {
    private Integer listaId;
    private Integer produtoReferenciaId;

    public ListaResultadoId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListaResultadoId that)) return false;
        return Objects.equals(listaId, that.listaId) &&
                Objects.equals(produtoReferenciaId, that.produtoReferenciaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listaId, produtoReferenciaId);
    }

    public Integer getListaId() {
        return listaId;
    }

    public Integer getProdutoReferenciaId() {
        return produtoReferenciaId;
    }
}
