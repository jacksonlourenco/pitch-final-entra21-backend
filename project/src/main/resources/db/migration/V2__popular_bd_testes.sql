
-- SEU BLOCO COMPLETO DE INSERTS FOI INSERIDO AQUI

INSERT INTO estabelecimento (id, nome) VALUES
(1, 'Supermercado Brasil'),
(2, 'Hiper BomPreço'),
(3, 'CompreBem');

INSERT INTO unidade (id, nome, bairro, cidade, estado, cep, latitude, longitude, estabelecimento_id) VALUES
(1, 'Unidade Centro', 'Centro', 'Blumenau', 'SC', '89010-000', '-26.9230', '-49.0651', 1),
(2, 'Unidade Norte', 'Salto do Norte', 'Blumenau', 'SC', '89065-000', '-26.8600', '-49.0800', 1),
(3, 'Unidade Velha', 'Velha', 'Blumenau', 'SC', '89036-000', '-26.9165', '-49.0712', 2);

INSERT INTO produto_referencia (id, url_img, descricao, marca, unidade_medida, valor_medida, codigo_barra, nome) VALUES
(1, 'https://img.com/arroz.jpg', 'Arroz Branco Tipo 1', 'Tio João', 'kg', 5.0, '7891025101001', 'Arroz 5kg'),
(2, 'https://img.com/feijao.jpg', 'Feijão Preto Selecionado', 'Kicaldo', 'kg', 1.0, '7891910000197', 'Feijão 1kg'),
(3, 'https://img.com/oleo.jpg', 'Óleo de Soja Refinado', 'Soya', 'L', 0.9, '7894900700012', 'Óleo de Soja 900ml'),
(4, 'https://img.com/leite.jpg', 'Leite Integral UHT', 'Italac', 'L', 1.0, '7896278900025', 'Leite 1L');

INSERT INTO produto_scraping (id, nome, preco, preco_especial, data_scraping, url_img, produto_referencia_id, unidade_id) VALUES
(1, 'Arroz Tio João 5kg', 25.90, 23.90, '2025-08-01', 'https://img.com/arroz.jpg', 1, 1),
(2, 'Feijão Preto Kicaldo 1kg', 8.50, 7.99, '2025-08-01', 'https://img.com/feijao.jpg', 2, 2),
(3, 'Óleo Soya 900ml', 6.20, 5.99, '2025-08-01', 'https://img.com/oleo.jpg', 3, 3),
(4, 'Leite Italac 1L', 4.80, 4.50, '2025-08-01', 'https://img.com/leite.jpg', 4, 1),
(5, 'Leite Italac 1L', 4.99, 4.49, '2025-08-01', 'https://img.com/leite.jpg', 4, 2);

INSERT INTO usuario (id, email, nome) VALUES
(1, 'joao@email.com', 'João Silva'),
(2, 'ana@email.com', 'Ana Souza');

INSERT INTO lista (id, nome, data_criacao, usuario_id) VALUES
(1, 'Compras Mês', '2025-07-31', 1),
(2, 'Lista da Semana', '2025-08-01', 2);

INSERT INTO lista_resultado (lista_id, produto_referencia_id, quantidade) VALUES
(1, 1, 2),  -- Arroz 5kg x2
(1, 2, 2),  -- Feijão 1kg x2
(1, 3, 1),  -- Óleo 900ml x1
(2, 4, 6);  -- Leite 1L x6

-- ADICIONANDO MAIS ESTABELECIMENTOS

INSERT INTO estabelecimento (id, nome) VALUES
(4, 'Atacadão'),
(5, 'Angeloni');

-- MAIS UNIDADES

INSERT INTO unidade (id, nome, bairro, cidade, estado, cep, latitude, longitude, estabelecimento_id) VALUES
(4, 'Unidade Garcia', 'Garcia', 'Blumenau', 'SC', '89020-000', '-26.9221', '-49.0750', 2),
(5, 'Unidade Itoupava', 'Itoupava Norte', 'Blumenau', 'SC', '89052-000', '-26.8764', '-49.0795', 4),
(6, 'Unidade Fortaleza', 'Fortaleza', 'Blumenau', 'SC', '89058-000', '-26.8666', '-49.0855', 5);

-- MAIS PRODUTOS REFERENCIA

INSERT INTO produto_referencia (id, url_img, descricao, marca, unidade_medida, valor_medida, codigo_barra, nome) VALUES
(5, 'https://img.com/acucar.jpg', 'Açúcar Refinado', 'União', 'kg', 1.0, '7891910000111', 'Açúcar 1kg'),
(6, 'https://img.com/cafe.jpg', 'Café Torrado e Moído', 'Melitta', 'g', 500.0, '7891025103003', 'Café 500g'),
(7, 'https://img.com/papel.jpg', 'Papel Higiênico Folha Dupla', 'Neve', 'un', 12.0, '7896028420195', 'Papel Higiênico 12un'),
(8, 'https://img.com/sabao.jpg', 'Sabão em Pó', 'OMO', 'kg', 1.6, '7891150050044', 'Sabão OMO 1.6kg');

-- MAIS PRODUTOS SCRAPING

INSERT INTO produto_scraping (id, nome, preco, preco_especial, data_scraping, url_img, produto_referencia_id, unidade_id) VALUES
(6, 'Açúcar União 1kg', 3.50, 3.29, '2025-08-01', 'https://img.com/acucar.jpg', 5, 1),
(7, 'Café Melitta 500g', 11.90, 10.90, '2025-08-01', 'https://img.com/cafe.jpg', 6, 2),
(8, 'Papel Neve 12un', 19.99, 18.90, '2025-08-01', 'https://img.com/papel.jpg', 7, 4),
(9, 'Sabão OMO 1.6kg', 24.90, 22.50, '2025-08-01', 'https://img.com/sabao.jpg', 8, 5),
(10, 'Açúcar União 1kg', 3.59, 3.19, '2025-08-02', 'https://img.com/acucar.jpg', 5, 3),
(11, 'Café Melitta 500g', 12.50, 11.50, '2025-08-02', 'https://img.com/cafe.jpg', 6, 4),
(12, 'Papel Neve 12un', 20.99, 19.49, '2025-08-02', 'https://img.com/papel.jpg', 7, 6),
(13, 'Sabão OMO 1.6kg', 25.99, 23.99, '2025-08-02', 'https://img.com/sabao.jpg', 8, 6);

-- MAIS USUÁRIOS

INSERT INTO usuario (id, email, nome) VALUES
(3, 'maria@email.com', 'Maria Oliveira'),
(4, 'carlos@email.com', 'Carlos Mendes');

-- MAIS LISTAS

INSERT INTO lista (id, nome, data_criacao, usuario_id) VALUES
(3, 'Limpeza e Café', '2025-08-01', 3),
(4, 'Compras da Quinzena', '2025-08-01', 4);

-- MAIS RESULTADOS DE LISTA

INSERT INTO lista_resultado (lista_id, produto_referencia_id, quantidade) VALUES
(3, 6, 2),  -- Café x2
(3, 7, 1),  -- Papel x1
(3, 8, 2),  -- Sabão x2
(4, 1, 1),  -- Arroz
(4, 2, 1),  -- Feijão
(4, 3, 1),  -- Óleo
(4, 5, 2);  -- Açúcar


-- MAIS 50 INSERTS DE PRODUTOS SCRAPING

INSERT INTO produto_scraping (id, nome, preco, preco_especial, data_scraping, url_img, produto_referencia_id, unidade_id) VALUES
(14, 'Arroz Tio João 5kg', 24.90, 22.90, '2025-08-02', 'https://img.com/arroz.jpg', 1, 1),
(15, 'Arroz Tio João 5kg', 23.50, 22.00, '2025-08-03', 'https://img.com/arroz.jpg', 1, 2),
(16, 'Arroz Tio João 5kg', 24.00, 22.50, '2025-08-04', 'https://img.com/arroz.jpg', 1, 3),
(17, 'Arroz Tio João 5kg', 24.50, 23.00, '2025-08-05', 'https://img.com/arroz.jpg', 1, 4),
(18, 'Arroz Tio João 5kg', 25.00, 23.50, '2025-08-06', 'https://img.com/arroz.jpg', 1, 5),
(19, 'Feijão Preto Kicaldo 1kg', 8.90, 7.99, '2025-08-02', 'https://img.com/feijao.jpg', 2, 1),
(20, 'Feijão Preto Kicaldo 1kg', 8.70, 7.70, '2025-08-03', 'https://img.com/feijao.jpg', 2, 2),
(21, 'Feijão Preto Kicaldo 1kg', 9.00, 8.00, '2025-08-04', 'https://img.com/feijao.jpg', 2, 3),
(22, 'Feijão Preto Kicaldo 1kg', 9.10, 8.20, '2025-08-05', 'https://img.com/feijao.jpg', 2, 4),
(23, 'Feijão Preto Kicaldo 1kg', 8.50, 7.90, '2025-08-06', 'https://img.com/feijao.jpg', 2, 5),
(24, 'Óleo Soya 900ml', 6.10, 5.80, '2025-08-02', 'https://img.com/oleo.jpg', 3, 1),
(25, 'Óleo Soya 900ml', 6.20, 5.90, '2025-08-03', 'https://img.com/oleo.jpg', 3, 2),
(26, 'Óleo Soya 900ml', 6.30, 6.00, '2025-08-04', 'https://img.com/oleo.jpg', 3, 3),
(27, 'Óleo Soya 900ml', 6.40, 6.10, '2025-08-05', 'https://img.com/oleo.jpg', 3, 4),
(28, 'Óleo Soya 900ml', 6.00, 5.70, '2025-08-06', 'https://img.com/oleo.jpg', 3, 5),
(29, 'Leite Italac 1L', 4.80, 4.50, '2025-08-02', 'https://img.com/leite.jpg', 4, 1),
(30, 'Leite Italac 1L', 4.75, 4.45, '2025-08-03', 'https://img.com/leite.jpg', 4, 2),
(31, 'Leite Italac 1L', 4.95, 4.65, '2025-08-04', 'https://img.com/leite.jpg', 4, 3),
(32, 'Leite Italac 1L', 5.00, 4.70, '2025-08-05', 'https://img.com/leite.jpg', 4, 4),
(33, 'Leite Italac 1L', 4.85, 4.55, '2025-08-06', 'https://img.com/leite.jpg', 4, 5),
(34, 'Açúcar União 1kg', 3.60, 3.30, '2025-08-02', 'https://img.com/acucar.jpg', 5, 1),
(35, 'Açúcar União 1kg', 3.55, 3.25, '2025-08-03', 'https://img.com/acucar.jpg', 5, 2),
(36, 'Açúcar União 1kg', 3.65, 3.35, '2025-08-04', 'https://img.com/acucar.jpg', 5, 3),
(37, 'Açúcar União 1kg', 3.70, 3.40, '2025-08-05', 'https://img.com/acucar.jpg', 5, 4),
(38, 'Açúcar União 1kg', 3.50, 3.20, '2025-08-06', 'https://img.com/acucar.jpg', 5, 5),
(39, 'Café Melitta 500g', 12.00, 11.50, '2025-08-02', 'https://img.com/cafe.jpg', 6, 1),
(40, 'Café Melitta 500g', 11.90, 11.30, '2025-08-03', 'https://img.com/cafe.jpg', 6, 2),
(41, 'Café Melitta 500g', 12.20, 11.70, '2025-08-04', 'https://img.com/cafe.jpg', 6, 3),
(42, 'Café Melitta 500g', 12.10, 11.60, '2025-08-05', 'https://img.com/cafe.jpg', 6, 4),
(43, 'Café Melitta 500g', 11.80, 11.20, '2025-08-06', 'https://img.com/cafe.jpg', 6, 5),
(44, 'Papel Neve 12un', 20.90, 19.90, '2025-08-02', 'https://img.com/papel.jpg', 7, 1),
(45, 'Papel Neve 12un', 21.00, 20.00, '2025-08-03', 'https://img.com/papel.jpg', 7, 2),
(46, 'Papel Neve 12un', 20.80, 19.80, '2025-08-04', 'https://img.com/papel.jpg', 7, 3),
(47, 'Papel Neve 12un', 20.70, 19.70, '2025-08-05', 'https://img.com/papel.jpg', 7, 4),
(48, 'Papel Neve 12un', 20.60, 19.60, '2025-08-06', 'https://img.com/papel.jpg', 7, 5),
(49, 'Sabão OMO 1.6kg', 26.00, 24.00, '2025-08-02', 'https://img.com/sabao.jpg', 8, 1),
(50, 'Sabão OMO 1.6kg', 25.90, 23.90, '2025-08-03', 'https://img.com/sabao.jpg', 8, 2),
(51, 'Sabão OMO 1.6kg', 25.80, 23.80, '2025-08-04', 'https://img.com/sabao.jpg', 8, 3),
(52, 'Sabão OMO 1.6kg', 25.70, 23.70, '2025-08-05', 'https://img.com/sabao.jpg', 8, 4),
(53, 'Sabão OMO 1.6kg', 25.60, 23.60, '2025-08-06', 'https://img.com/sabao.jpg', 8, 5);
