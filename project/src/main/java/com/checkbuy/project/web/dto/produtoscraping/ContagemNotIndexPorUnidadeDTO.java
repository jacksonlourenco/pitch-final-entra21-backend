package com.checkbuy.project.web.dto.produtoscraping;

public record ContagemNotIndexPorUnidadeDTO(
    String estabelecimentoNome,
    Integer unidadeId,
    String unidadeNome,
    Long contagem
){
    public ContagemNotIndexPorUnidadeDTO(String estabelecimentoNome, Integer unidadeId, String unidadeNome, Long contagem) {
        this.estabelecimentoNome = estabelecimentoNome;
        this.unidadeId = unidadeId;
        this.unidadeNome = unidadeNome;
        this.contagem = contagem;
    }
}


