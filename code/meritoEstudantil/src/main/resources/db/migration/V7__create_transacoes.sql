CREATE TABLE transacoes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    professor_id BIGINT,
    aluno_id BIGINT NOT NULL,
    quantidade_moedas INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descricao VARCHAR(500),
    data_criacao DATETIME NOT NULL,
    vantagem_id BIGINT,
    FOREIGN KEY (professor_id) REFERENCES professor(id) ON DELETE SET NULL,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE,
    FOREIGN KEY (vantagem_id) REFERENCES vantagens(id) ON DELETE SET NULL
);