INSERT INTO estabelecimento (nome) VALUES
('Koch');

INSERT INTO unidade (nome, bairro, municipio, estado, cep, latitude, longitude, estabelecimento_id) VALUES
('Koch', 'online', 'online', 'online', 'online', NULL, NULL, 2);

INSERT INTO alias_unidade(alias, unidade_id) VALUES
("koch-bnu", 6);
