
-- 1. Produtos de uma lista e onde encontrá-los mais baratos

SELECT
    produto_referencia.nome,
    produto_referencia.marca,
    produto_referencia.url_img,
    lista_resultado.quantidade,
    produto_scraping.preco,
    produto_scraping.preco_especial,
    produto_scraping.data_scraping,
    estabelecimento.nome,
    unidade.nome
FROM
    lista_resultado
JOIN produto_referencia ON lista_resultado.produto_referencia_id = produto_referencia.id
JOIN produto_scraping ON produto_referencia.id = produto_scraping.produto_referencia_id
JOIN unidade ON produto_scraping.unidade_id = unidade.id
JOIN estabelecimento ON unidade.estabelecimento_id = estabelecimento.id
WHERE
    lista_resultado.lista_id = 1
ORDER BY
    produto_referencia.nome,
    produto_scraping.preco ASC;


-- 2. Menor preço encontrado por produto

SELECT
    produto_referencia.nome,
    MIN(produto_scraping.preco)
FROM
    produto_scraping
JOIN produto_referencia ON produto_scraping.produto_referencia_id = produto_referencia.id
GROUP BY
    produto_referencia.nome
ORDER BY
    MIN(produto_scraping.preco) ASC;


-- 3. Onde encontrar o menor preço de cada produto de uma lista

SELECT
    produto_referencia.nome,
    produto_scraping.preco,
    produto_scraping.preco_especial,
    produto_scraping.data_scraping,
    estabelecimento.nome,
    unidade.nome
FROM
    lista_resultado
JOIN produto_referencia ON lista_resultado.produto_referencia_id = produto_referencia.id
JOIN produto_scraping ON produto_referencia.id = produto_scraping.produto_referencia_id
JOIN unidade ON produto_scraping.unidade_id = unidade.id
JOIN estabelecimento ON unidade.estabelecimento_id = estabelecimento.id
WHERE
    lista_resultado.lista_id = 1
    AND produto_scraping.preco = (
        SELECT MIN(produto_scraping_2.preco)
        FROM produto_scraping AS produto_scraping_2
        WHERE produto_scraping_2.produto_referencia_id = produto_referencia.id
    )
ORDER BY
    produto_referencia.nome;


-- 4. Total estimado da lista de compras com os menores preços

SELECT
    SUM(lista_resultado.quantidade * (
        SELECT MIN(produto_scraping.preco)
        FROM produto_scraping
        WHERE produto_scraping.produto_referencia_id = lista_resultado.produto_referencia_id
    )) AS total_estimado
FROM
    lista_resultado
WHERE
    lista_resultado.lista_id = 1;


-- 5. Todas as listas criadas por um usuário

SELECT
    lista.id,
    lista.nome,
    lista.data_criacao
FROM
    lista
JOIN usuario ON lista.usuario_id = usuario.id
WHERE
    usuario.email = 'joao@email.com';


-- 6. Histórico de preços de um produto específico

SELECT
    produto_scraping.data_scraping,
    produto_scraping.preco,
    produto_scraping.preco_especial,
    estabelecimento.nome,
    unidade.nome
FROM
    produto_scraping
JOIN unidade ON produto_scraping.unidade_id = unidade.id
JOIN estabelecimento ON unidade.estabelecimento_id = estabelecimento.id
WHERE
    produto_scraping.produto_referencia_id = 1
ORDER BY
    produto_scraping.data_scraping DESC;
