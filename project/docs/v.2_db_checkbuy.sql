CREATE TABLE usuario (
    id INT AUTO_INCREMENT,
    email VARCHAR(45),
    nome VARCHAR(45),
    PRIMARY KEY (id)
);

CREATE TABLE estabelecimento (
    id INT AUTO_INCREMENT,
    nome VARCHAR(45),
    PRIMARY KEY (id)
);

CREATE TABLE unidade (
    id INT AUTO_INCREMENT,
    nome VARCHAR(45),
    bairro VARCHAR(45),
    cidade VARCHAR(45),
    estado VARCHAR(45),
    cep VARCHAR(45),
    latitude VARCHAR(45),
    longitude VARCHAR(45),
    estabelecimento_id INT NOT NULL,
    
    PRIMARY KEY (id),
    FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id)
);

CREATE TABLE produto_referencia (
    id INT AUTO_INCREMENT,
    url_img VARCHAR(500),
    descricao VARCHAR(100),
    marca VARCHAR(45),
    unidade_medida VARCHAR(45),
    valor_medida FLOAT,
    codigo_barra VARCHAR(70),
    nome VARCHAR(45),
    PRIMARY KEY (id)
);

CREATE TABLE lista (
    id INT AUTO_INCREMENT,
    nome VARCHAR(45),
    data_criacao DATE,
    usuario_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE produto_scraping (
    id INT AUTO_INCREMENT,
    nome VARCHAR(45),
    preco DOUBLE,
    preco_especial DOUBLE,
    data_scraping DATE,
    url_imagem VARCHAR(500),
    unidade_id INT NOT NULL,
    produto_referencia_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (unidade_id) REFERENCES unidade(id),
    FOREIGN KEY (produto_referencia_id) REFERENCES produto_referencia(id)
);

CREATE TABLE lista_resultado (
    lista_id INT NOT NULL,
    produto_referencia_id INT NOT NULL,
    produto_scraping INT NOT NULL,
    valor DOUBLE,
    valor_especial DOUBLE,
    PRIMARY KEY (lista_id, produto_referencia_id, produto_scraping),
    FOREIGN KEY (lista_id) REFERENCES lista(id),
    FOREIGN KEY (produto_referencia_id) REFERENCES produto_referencia(id),
    FOREIGN KEY (produto_scraping) REFERENCES produto_scraping(id)
);