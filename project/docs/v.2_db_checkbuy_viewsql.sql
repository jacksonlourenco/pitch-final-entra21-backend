SELECT
    produto_referencia.id,
    produto_referencia.nome,
    produto_scraping.preco,
    produto_scraping.preco_especial,
    produto_scraping.data_scraping,
    unidade.nome
FROM
    lista_resultado
JOIN
    produto_referencia ON lista_resultado.produto_referencia_id = produto_referencia.id
JOIN
    produto_scraping ON lista_resultado.produto_scraping = produto_scraping.id
JOIN
    estabelecimento ON produto_scraping.estabelecimento_id = estabelecimento.id
ORDER BY
    produto_referencia.id, produto_scraping.data_scraping DESC;