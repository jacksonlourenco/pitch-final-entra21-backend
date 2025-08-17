package com.checkbuy.project.domain.dto;

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


