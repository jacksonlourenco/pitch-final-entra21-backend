INSERT INTO estabelecimento (nome) VALUES
('Bistek');

INSERT INTO unidade (nome, bairro, municipio, estado, cep, latitude, longitude, estabelecimento_id) VALUES
('Bistek', 'online', 'online', 'online', 'online', NULL, NULL, 3);

INSERT INTO alias_unidade(alias, unidade_id) VALUES
("bistek-bnu", 7);
