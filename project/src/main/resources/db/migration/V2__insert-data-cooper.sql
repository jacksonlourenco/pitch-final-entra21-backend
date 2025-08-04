INSERT INTO estabelecimento (nome) VALUES
('Cooper');

INSERT INTO unidade (nome, bairro, municipio, estado, cep, latitude, longitude, estabelecimento_id) VALUES
('Unidade Água Verde', 'Água Verde', 'Blumenau', 'SC', '89041002', NULL, NULL, 1),
('Unidade Garcia', 'Garcia', 'Blumenau', 'SC', '89022000', NULL, NULL, 1),
('Unidade Itoupava Norte', 'Itoupava Norte', 'Blumenau', 'SC', '89052504', NULL, NULL, 1),
('Unidade Mafisa', 'Itoupavazinha', 'Blumenau', 'SC', '89056600', NULL, NULL, 1),
('Unidade Vila Nova', 'Vila Nova', 'Blumenau', 'SC', '89035100', NULL, NULL, 1);

INSERT INTO alias_unidade(alias, unidade_id) VALUES
("a.verde-bnu", 1),
("garcia-bnu", 2),
("i.norte-bnu", 3),
("mafisa-bnu", 4),
("v.nova-bnu", 5);
