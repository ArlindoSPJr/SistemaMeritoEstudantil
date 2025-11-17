CREATE TABLE transacoes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    professor_id BIGINT NOT NULL,
    aluno_id BIGINT NOT NULL,
    quantidade_moedas INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descricao VARCHAR(500),
    data_criacao DATETIME NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES professor(id) ON DELETE CASCADE,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE
);
