CREATE TABLE tb_roles(
	role_id VARCHAR(45) NOT NULL,
	name VARCHAR(45) NOT NULL,
	PRIMARY KEY (role_id)
);

CREATE TABLE tb_users(
	user_id INT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(45),
	senha VARCHAR(45),
	email VARCHAR(45),
	cpf VARCHAR(11),
	telefone VARCHAR(11),
	roles VARCHAR(45),
	PRIMARY KEY (user_id),
	FOREIGN KEY (roles) REFERENCES tb_roles(role_id)
);
