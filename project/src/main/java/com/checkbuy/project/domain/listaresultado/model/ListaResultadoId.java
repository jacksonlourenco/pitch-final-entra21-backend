package com.checkbuy.project.domain.listaresultado.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class ListaResultadoId implements Serializable {
    private Integer listaId;
    private Integer produtoReferenciaId;

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

}
