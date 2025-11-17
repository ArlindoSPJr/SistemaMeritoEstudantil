-- V5: cria tabela vantagens (com FK para empresas_parceiras)

CREATE TABLE IF NOT EXISTS vantagens (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  ativo BOOLEAN DEFAULT TRUE,
  image_url VARCHAR(500),
  custo_moedas DOUBLE NOT NULL,
  empresa_parceira_id BIGINT,
  CONSTRAINT fk_vantagem_empresa FOREIGN KEY (empresa_parceira_id)
    REFERENCES empresas_parceiras(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
