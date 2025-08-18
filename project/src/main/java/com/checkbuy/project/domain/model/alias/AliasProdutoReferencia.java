package com.checkbuy.project.domain.model.alias;

import com.checkbuy.project.domain.model.ProdutoReferencia;
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
@Table(name = "alias_produto_referencia")
public class AliasProdutoReferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String alias;

    @ManyToOne
    @JoinColumn(name = "produto_referencia_id", nullable = false)
    private ProdutoReferencia produtoReferencia;
}
