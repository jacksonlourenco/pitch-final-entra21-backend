package com.checkbuy.project.web.dto.produtoreferencia;

import com.checkbuy.project.domain.produtoscraping.model.ProdutoScraping;

import java.time.LocalDateTime;

public record ProdutoScrapingSimilaridadeDTO(
        Double similaridade,
        Integer id,
        String nome,
        String urlImg,
        LocalDateTime data,
        String unidade,
        String estabelecimento
){
    // Construtor que recebe ProdutoReferencia e similaridade
    public ProdutoScrapingSimilaridadeDTO() {
        this(0.0, -1, "", "", LocalDateTime.now(), "", "");
    }

    public ProdutoScrapingSimilaridadeDTO(ProdutoScraping first, double similaridade) {
        this(similaridade, first.getId(), first.getNome(), first.getUrlImg(), first.getDataScraping(), first.getUnidade().getNome(), first.getUnidade().getEstabelecimento().getNome());
    }
}
