CREATE TABLE estabelecimento(
	id INT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(45),

	PRIMARY KEY (id)
);

CREATE TABLE unidade(
	id INT NOT NULL AUTO_INCREMENT,
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
	id INT NOT NULL AUTO_INCREMENT,
	url_img VARCHAR(500),
	descricao VARCHAR(500),
	marca VARCHAR(45),
	unidade_medida VARCHAR(45),
	valor_medida DOUBLE,
	codigo_barra VARCHAR(70),
	nome VARCHAR(255),

	PRIMARY KEY(id)
);

CREATE TABLE produto_scraping(
	id INT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(255),
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
	id INT NOT NULL AUTO_INCREMENT,
	email VARCHAR(45),
	nome VARCHAR(45),
	senha VARCHAR(500),

	PRIMARY KEY(id)
);

CREATE TABLE lista(
	id INT NOT NULL AUTO_INCREMENT,
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

CREATE TABLE alias_unidade (
    id INT NOT NULL AUTO_INCREMENT,
    alias VARCHAR(100),
    unidade_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (unidade_id) REFERENCES unidade(id)
);

CREATE TABLE alias_produto_referencia(
    id INT NOT NULL AUTO_INCREMENT,
    alias VARCHAR(255),
    produto_referencia_id INT,

    PRIMARY KEY (id),
    FOREIGN KEY (produto_referencia_id) REFERENCES produto_referencia(id)
);

INSERT INTO produto_referencia(nome) VALUES
("NOT INDEX");

INSERT INTO alias_produto_referencia(alias, produto_referencia_id) VALUES
("NOT INDEX", 1);