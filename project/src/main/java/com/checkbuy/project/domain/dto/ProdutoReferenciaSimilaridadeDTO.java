package com.checkbuy.project.domain.dto;

import com.checkbuy.project.domain.model.ProdutoReferencia;

public record ProdutoReferenciaSimilaridadeDTO(
        Double similaridade,
        Integer id,
        String nome,
        String urlImg,
        String marca,
        String descricao,
        String unidadeMedida,
        Double valorMedida,
        String codigoBarra
){
    // Construtor que recebe ProdutoReferencia e similaridade
    public ProdutoReferenciaSimilaridadeDTO(ProdutoReferencia p, Double similaridade) {
        this(
                similaridade,
                p.getId(),
                p.getNome(),
                p.getUrlImg(),
                p.getMarca(),
                p.getDescricao(),
                p.getUnidadeMedida(),
                p.getValorMedida(),
                p.getCodigoBarra()
        );
    }
}
