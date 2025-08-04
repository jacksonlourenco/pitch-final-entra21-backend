package com.example.project.domain.dto;

public record ProdutoNotIndexDTO(
        String nomeEstabelecimento,
        String nomeProduto,
        String urlImg,
        Integer produtoReferenciaId
){}
