CREATE TABLE usuario(
	id_usuario INT AUTO_INCREMENT,
	email VARCHAR(255),
	nome VARCHAR(50),
	
	PRIMARY KEY(id_usuario)
);

INSERT INTO usuario(email, nome)
VALUES
('brunna@ewelly', 'Brunna Ewelly');

SELECT * FROM usuario;


CREATE TABLE lista(
	id_lista INT AUTO_INCREMENT,
	nome VARCHAR(50),
	data_criacao DATE,
	id_usuario INT,
	
	PRIMARY KEY(id_lista),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

INSERT INTO lista(nome, data_criacao, id_usuario)
VALUES
('Lista de Churrasco', '2025-07-29', 1),
('Lista de Aniversário', '2025-07-29', 1);

SELECT * FROM lista;


SELECT * FROM usuario
JOIN lista
ON
usuario.id_usuario = lista.id_usuario;

CREATE TABLE produto_referencia





