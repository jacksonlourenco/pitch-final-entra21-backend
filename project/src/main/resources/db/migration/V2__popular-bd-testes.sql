INSERT INTO estabelecimento (nome) VALUES
('Supermercado Brasil'),
('Hiper BomPreço'),
('CompreBem'),
('Atacadão'),
('Angeloni');


INSERT INTO unidade (nome, bairro, municipio, estado, cep, latitude, longitude, estabelecimento_id) VALUES
('Unidade Centro', 'Centro', 'Blumenau', 'SC', '89010-000', '-26.9230', '-49.0651', 1),
('Unidade Norte', 'Salto do Norte', 'Blumenau', 'SC', '89065-000', '-26.8600', '-49.0800', 1),
('Unidade Velha', 'Velha', 'Blumenau', 'SC', '89036-000', '-26.9165', '-49.0712', 2),
('Unidade Garcia', 'Garcia', 'Blumenau', 'SC', '89020-000', '-26.9221', '-49.0750', 2),
('Unidade Itoupava', 'Itoupava Norte', 'Blumenau', 'SC', '89052-000', '-26.8764', '-49.0795', 4),
('Unidade Fortaleza', 'Fortaleza', 'Blumenau', 'SC', '89058-000', '-26.8666', '-49.0855', 5);

INSERT INTO produto_referencia (url_img, descricao, marca, unidade_medida, valor_medida, codigo_barra, nome) VALUES
('https://img.com/arroz.jpg', 'Arroz Branco Tipo 1', 'Tio João', 'kg', 5.0, '7891025101001', 'Arroz 5kg'),
('https://img.com/feijao.jpg', 'Feijão Preto Selecionado', 'Kicaldo', 'kg', 1.0, '7891910000197', 'Feijão 1kg'),
('https://img.com/oleo.jpg', 'Óleo de Soja Refinado', 'Soya', 'L', 0.9, '7894900700012', 'Óleo de Soja 900ml'),
('https://img.com/leite.jpg', 'Leite Integral UHT', 'Italac', 'L', 1.0, '7896278900025', 'Leite 1L'),
('https://img.com/acucar.jpg', 'Açúcar Refinado', 'União', 'kg', 1.0, '7891910000111', 'Açúcar 1kg'),
('https://img.com/cafe.jpg', 'Café Torrado e Moído', 'Melitta', 'g', 500.0, '7891025103003', 'Café 500g'),
('https://img.com/papel.jpg', 'Papel Higiênico Folha Dupla', 'Neve', 'un', 12.0, '7896028420195', 'Papel Higiênico 12un'),
('https://img.com/sabao.jpg', 'Sabão em Pó', 'OMO', 'kg', 1.6, '7891150050044', 'Sabão OMO 1.6kg');

INSERT INTO produto_scraping (nome, preco, preco_especial, data_scraping, url_img, produto_referencia_id, unidade_id) VALUES
('Arroz Tio João 5kg', 25.90, 23.90, '2025-08-01 00:00:00', 'https://img.com/arroz.jpg', 1, 1),
('Feijão Preto Kicaldo 1kg', 8.50, 7.99, '2025-08-01 00:00:00', 'https://img.com/feijao.jpg', 2, 2),
('Óleo Soya 900ml', 6.20, 5.99, '2025-08-01 00:00:00', 'https://img.com/oleo.jpg', 3, 3),
('Leite Italac 1L', 4.80, 4.50, '2025-08-01 00:00:00', 'https://img.com/leite.jpg', 4, 1),
('Leite Italac 1L', 4.99, 4.49, '2025-08-01 00:00:00', 'https://img.com/leite.jpg', 4, 2),
('Açúcar União 1kg', 3.50, 3.29, '2025-08-01 00:00:00', 'https://img.com/acucar.jpg', 5, 1),
('Café Melitta 500g', 11.90, 10.90, '2025-08-01 00:00:00', 'https://img.com/cafe.jpg', 6, 2),
('Papel Neve 12un', 19.99, 18.90, '2025-08-01 00:00:00', 'https://img.com/papel.jpg', 7, 4),
('Sabão OMO 1.6kg', 24.90, 22.50, '2025-08-01 00:00:00', 'https://img.com/sabao.jpg', 8, 5),
('Açúcar União 1kg', 3.59, 3.19, '2025-08-02 00:00:00', 'https://img.com/acucar.jpg', 5, 3),
('Café Melitta 500g', 12.50, 11.50, '2025-08-02 00:00:00', 'https://img.com/cafe.jpg', 6, 4),
('Papel Neve 12un', 20.99, 19.49, '2025-08-02 00:00:00', 'https://img.com/papel.jpg', 7, 6),
('Sabão OMO 1.6kg', 25.99, 23.99, '2025-08-02 00:00:00', 'https://img.com/sabao.jpg', 8, 6),
('Arroz Tio João 5kg', 24.90, 22.90, '2025-08-02 00:00:00', 'https://img.com/arroz.jpg', 1, 1),
('Arroz Tio João 5kg', 23.50, 22.00, '2025-08-03 00:00:00', 'https://img.com/arroz.jpg', 1, 2),
('Arroz Tio João 5kg', 24.00, 22.50, '2025-08-04 00:00:00', 'https://img.com/arroz.jpg', 1, 3),
('Arroz Tio João 5kg', 24.50, 23.00, '2025-08-05 00:00:00', 'https://img.com/arroz.jpg', 1, 4),
('Arroz Tio João 5kg', 25.00, 23.50, '2025-08-06 00:00:00', 'https://img.com/arroz.jpg', 1, 5),
('Feijão Preto Kicaldo 1kg', 8.90, 7.99, '2025-08-02 00:00:00', 'https://img.com/feijao.jpg', 2, 1),
('Feijão Preto Kicaldo 1kg', 8.70, 7.70, '2025-08-03 00:00:00', 'https://img.com/feijao.jpg', 2, 2),
('Feijão Preto Kicaldo 1kg', 9.00, 8.00, '2025-08-04 00:00:00', 'https://img.com/feijao.jpg', 2, 3),
('Feijão Preto Kicaldo 1kg', 9.10, 8.20, '2025-08-05 00:00:00', 'https://img.com/feijao.jpg', 2, 4),
('Feijão Preto Kicaldo 1kg', 8.50, 7.90, '2025-08-06 00:00:00', 'https://img.com/feijao.jpg', 2, 5),
('Óleo Soya 900ml', 6.10, 5.80, '2025-08-02 00:00:00', 'https://img.com/oleo.jpg', 3, 1),
('Óleo Soya 900ml', 6.20, 5.90, '2025-08-03 00:00:00', 'https://img.com/oleo.jpg', 3, 2),
('Óleo Soya 900ml', 6.30, 6.00, '2025-08-04 00:00:00', 'https://img.com/oleo.jpg', 3, 3),
('Óleo Soya 900ml', 6.40, 6.10, '2025-08-05 00:00:00', 'https://img.com/oleo.jpg', 3, 4),
('Óleo Soya 900ml', 6.00, 5.70, '2025-08-06 00:00:00', 'https://img.com/oleo.jpg', 3, 5),
('Leite Italac 1L', 4.80, 4.50, '2025-08-02 00:00:00', 'https://img.com/leite.jpg', 4, 1),
('Leite Italac 1L', 4.75, 4.45, '2025-08-03 00:00:00', 'https://img.com/leite.jpg', 4, 2),
('Leite Italac 1L', 4.95, 4.65, '2025-08-04 00:00:00', 'https://img.com/leite.jpg', 4, 3),
('Leite Italac 1L', 5.00, 4.70, '2025-08-05 00:00:00', 'https://img.com/leite.jpg', 4, 4),
('Leite Italac 1L', 4.85, 4.55, '2025-08-06 00:00:00', 'https://img.com/leite.jpg', 4, 5),
('Açúcar União 1kg', 3.60, 3.30, '2025-08-02 00:00:00', 'https://img.com/acucar.jpg', 5, 1),
('Açúcar União 1kg', 3.55, 3.25, '2025-08-03 00:00:00', 'https://img.com/acucar.jpg', 5, 2),
('Açúcar União 1kg', 3.65, 3.35, '2025-08-04 00:00:00', 'https://img.com/acucar.jpg', 5, 3),
('Açúcar União 1kg', 3.70, 3.40, '2025-08-05 00:00:00', 'https://img.com/acucar.jpg', 5, 4),
('Açúcar União 1kg', 3.50, 3.20, '2025-08-06 00:00:00', 'https://img.com/acucar.jpg', 5, 5),
('Café Melitta 500g', 12.00, 11.50, '2025-08-02 00:00:00', 'https://img.com/cafe.jpg', 6, 1),
('Café Melitta 500g', 11.90, 11.30, '2025-08-03 00:00:00', 'https://img.com/cafe.jpg', 6, 2),
('Café Melitta 500g', 12.20, 11.70, '2025-08-04 00:00:00', 'https://img.com/cafe.jpg', 6, 3),
('Café Melitta 500g', 12.10, 11.60, '2025-08-05 00:00:00', 'https://img.com/cafe.jpg', 6, 4),
('Café Melitta 500g', 11.80, 11.20, '2025-08-06 00:00:00', 'https://img.com/cafe.jpg', 6, 5),
('Papel Neve 12un', 20.90, 19.90, '2025-08-02 00:00:00', 'https://img.com/papel.jpg', 7, 1),
('Papel Neve 12un', 21.00, 20.00, '2025-08-03 00:00:00', 'https://img.com/papel.jpg', 7, 2),
('Papel Neve 12un', 20.80, 19.80, '2025-08-04 00:00:00', 'https://img.com/papel.jpg', 7, 3),
('Papel Neve 12un', 20.70, 19.70, '2025-08-05 00:00:00', 'https://img.com/papel.jpg', 7, 4),
('Papel Neve 12un', 20.60, 19.60, '2025-08-06 00:00:00', 'https://img.com/papel.jpg', 7, 5),
('Sabão OMO 1.6kg', 26.00, 24.00, '2025-08-02 00:00:00', 'https://img.com/sabao.jpg', 8, 1),
('Sabão OMO 1.6kg', 25.90, 23.90, '2025-08-03 00:00:00', 'https://img.com/sabao.jpg', 8, 2),
('Sabão OMO 1.6kg', 25.80, 23.80, '2025-08-04 00:00:00', 'https://img.com/sabao.jpg', 8, 3),
('Sabão OMO 1.6kg', 25.70, 23.70, '2025-08-05 00:00:00', 'https://img.com/sabao.jpg', 8, 4),
('Sabão OMO 1.6kg', 25.60, 23.60, '2025-08-06 00:00:00', 'https://img.com/sabao.jpg', 8, 5);


INSERT INTO usuario (email, nome) VALUES
('joao@email.com', 'João Silva'),
('ana@email.com', 'Ana Souza'),
('maria@email.com', 'Maria Oliveira'),
('carlos@email.com', 'Carlos Mendes');

INSERT INTO lista (nome, data_criacao, usuario_id) VALUES
('Compras Mês', '2025-07-31', 1),
('Lista da Semana', '2025-08-01', 2),
('Limpeza e Café', '2025-08-01', 3),
('Compras da Quinzena', '2025-08-01', 4);


INSERT INTO lista_resultado (lista_id, produto_referencia_id, quantidade) VALUES
(1, 1, 2),
(1, 2, 2),
(1, 3, 1),
(2, 4, 6),
(3, 6, 2),
(3, 7, 1),
(3, 8, 2),
(4, 1, 1),
(4, 2, 1),
(4, 3, 1),
(4, 5, 2);