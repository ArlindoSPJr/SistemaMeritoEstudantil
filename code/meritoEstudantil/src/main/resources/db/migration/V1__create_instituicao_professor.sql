-- V1: cria tabela instituicao_ensino e insere dados iniciais

CREATE TABLE IF NOT EXISTS instituicao_ensino (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  endereco VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dados iniciais para instituicao_ensino
INSERT INTO instituicao_ensino (nome, endereco) VALUES
  ('Universidade Federal Exemplo', 'Av. Central, 1000'),
  ('Instituto Tecnico Modelo', 'Rua do Saber, 45');
