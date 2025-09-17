package com.checkbuy.project.web.dto.scraping;

public record ProdutoDTO(
        String nome,
        String urlImg,
        double preco,
        double precoEspecial
){}
