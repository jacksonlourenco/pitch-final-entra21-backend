CREATE TABLE alias_unidade (
    id INT NOT NULL AUTO_INCREMENT,
    alias VARCHAR(100),
    unidade_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (unidade_id) REFERENCES unidade(id)
);