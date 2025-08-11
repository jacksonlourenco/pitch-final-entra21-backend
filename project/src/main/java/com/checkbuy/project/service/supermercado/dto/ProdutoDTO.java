package com.checkbuy.project.service.supermercado.dto;

public record ProdutoDTO(
        String nome,
        String urlImg,
        double preco,
        double precoEspecial
){}
