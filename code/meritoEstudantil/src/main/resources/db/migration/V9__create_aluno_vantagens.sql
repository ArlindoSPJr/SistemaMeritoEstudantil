-- V9: Criar tabela de junção aluno_vantagens para relacionamento Many-to-Many

CREATE TABLE IF NOT EXISTS aluno_vantagens (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  aluno_id BIGINT NOT NULL,
  vantagem_id BIGINT NOT NULL,
  data_resgate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_aluno_vantagem (aluno_id, vantagem_id),
  CONSTRAINT fk_aluno_vantagens_aluno FOREIGN KEY (aluno_id)
    REFERENCES aluno(id) ON DELETE CASCADE,
  CONSTRAINT fk_aluno_vantagens_vantagem FOREIGN KEY (vantagem_id)
    REFERENCES vantagens(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
