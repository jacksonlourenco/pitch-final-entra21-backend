package com.checkbuy.project.web.dto.produtoscraping;

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