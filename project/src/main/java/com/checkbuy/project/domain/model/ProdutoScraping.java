package com.checkbuy.project.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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

}
