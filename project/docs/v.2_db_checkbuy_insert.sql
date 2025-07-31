INSERT INTO usuario (email, nome) VALUES
('joao@email.com', 'João Silva'),
('maria@email.com', 'Maria Souza');

INSERT INTO estabelecimento (nome) VALUES
('Supermercado Central'),
('Mercado Econômico');

INSERT INTO unidade (nome, bairro, cidade, estado, cep, latitude, longitude, estabelecimento_id) VALUES
('Unidade Centro', 'Centro', 'Blumenau', 'SC', '89010-000', '-26.9194', '-49.0661', 1),
('Unidade Garcia', 'Garcia', 'Blumenau', 'SC', '89020-000', '-26.9321', '-49.0589', 2);

INSERT INTO produto_referencia (url_img, descricao, marca, unidade_medida, valor_medida, codigo_barra, nome) VALUES
('https://exemplo.com/leite.jpg', 'Leite UHT Integral 1L', 'Nestlé', 'L', 1.0, '7891000245214', 'Leite Integral'),
('https://exemplo.com/arroz.jpg', 'Arroz Branco Tipo 1 5kg', 'Tio João', 'kg', 5.0, '7896004000018', 'Arroz 5kg');

INSERT INTO lista (nome, data_criacao, usuario_id) VALUES
('Lista do Mês', '2025-07-30', 1),
('Compras Semanais', '2025-07-31', 2);

INSERT INTO produto_scraping (nome, preco, preco_especial, data_scraping, url_imagem, estabelecimento_id, produto_referencia_id) VALUES
('Leite Integral Nestlé', 4.89, 4.50, '2025-07-31', 'https://exemplo.com/leite.jpg', 1, 1),
('Arroz Branco Tio João', 19.90, 18.90, '2025-07-31', 'https://exemplo.com/arroz.jpg', 2, 2);

INSERT INTO lista_resultado (lista_id, produto_referencia_id, produto_scraping, valor, valor_especial) VALUES
(1, 1, 1, 4.89, 4.50),
(2, 2, 2, 19.90, 18.90);