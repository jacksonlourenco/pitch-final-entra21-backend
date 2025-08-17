package com.checkbuy.project.domain.dto;

import java.time.LocalDateTime;

public record ProdutoScrapingOfertaRecentesDTO(
        Integer id,
        String nomeProduto,
        Double preco,
        Double precoEspecial,
        LocalDateTime dataScraping,
        Integer unidadeId,
        String unidadeNome,
        String estabelecimentoNome
) {}