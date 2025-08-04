package com.checkbuy.project.domain.dto;

public record ProdutoNotIndexDTO(
        String nomeEstabelecimento,
        String nomeProduto,
        String urlImg,
        Integer produtoReferenciaId
){}
