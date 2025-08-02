CREATE TABLE estabelecimento(
	id INT NOT NULL,
	nome VARCHAR(45),

	PRIMARY KEY (id)
);

CREATE TABLE unidade(
	id INT NOT NULL,
	nome VARCHAR(45),
	bairro VARCHAR(45),
	municipio VARCHAR(45),
	estado VARCHAR(45),
	cep VARCHAR(45),
	latitude VARCHAR(45),
	longitude VARCHAR(45),
	estabelecimento_id INT,

	PRIMARY KEY (id),
	FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id)
);

CREATE TABLE produto_referencia(
	id INT NOT NULL,
	url_img VARCHAR(500),
	descricao VARCHAR(100),
	marca VARCHAR(45),
	unidade_medida VARCHAR(45),
	valor_medida DOUBLE,
	codigo_barra VARCHAR(70),
	nome VARCHAR(45),

	PRIMARY KEY(id)
);

CREATE TABLE produto_scraping(
	id INT NOT NULL,
	nome VARCHAR(45),
	preco DOUBLE,
	preco_especial DOUBLE,
	data_scraping DATETIME,
	url_img VARCHAR(500),
	produto_referencia_id INT,
	unidade_id INT,

	PRIMARY KEY(id),
	FOREIGN KEY (produto_referencia_id) REFERENCES produto_referencia(id),
	FOREIGN KEY(unidade_id) REFERENCES unidade(id)
);

CREATE TABLE usuario(
	id INT NOT NULL,
	email VARCHAR(45),
	nome VARCHAR(45),

	PRIMARY KEY(id)
);

CREATE TABLE lista(
	id INT NOT NULL,
	nome VARCHAR(45),
	data_criacao DATE,
	usuario_id INT,

	PRIMARY KEY(id),
	FOREIGN KEY(usuario_id) REFERENCES usuario(id)
);

CREATE TABLE lista_resultado(
	lista_id INT NOT NULL,
	produto_referencia_id INT NOT NULL,
	quantidade INT,

	PRIMARY KEY(lista_id, produto_referencia_id),
	FOREIGN KEY(lista_id) REFERENCES lista(id),
	FOREIGN KEY(produto_referencia_id) REFERENCES produto_referencia(id)
);