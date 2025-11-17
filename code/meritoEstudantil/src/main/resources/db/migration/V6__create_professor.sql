CREATE TABLE professor (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    departamento VARCHAR(255) NOT NULL,
    saldo_moedas INT NOT NULL DEFAULT 0,
    instituicao_ensino_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'PROFESSOR',
    FOREIGN KEY (instituicao_ensino_id) REFERENCES instituicao_ensino(id) ON DELETE CASCADE
);

-- Dados iniciais para professor
INSERT INTO professor (email, senha, nome, cpf, departamento, saldo_moedas, instituicao_ensino_id, role) VALUES
  ('ana.silva@univ.com', '$2a$10$ncaHAj.rSCLe7DLJBp9kPOmUg9HBd12tlqWduHg.PfxJh1H02f6r.', 'Ana Silva', '12345678901', 'Ciências da Computação', 120, 1, 'PROFESSOR'),
  ('bruno.souza@univ.com', '$2a$10$u852xGgvYpFtLfJpce8PHOOidDNAxzcjcChuoBtfMGQFsqbw54CEu', 'Bruno Souza', '98765432111', 'Engenharia', 80, 1, 'PROFESSOR'),
  ('carlos.pereira@inst.com', '$2a$10$Wel5JBbV/GBcDgNr.cXvwuipjiBijQHqnwFfxxOkqPlk7FCmLQXjq', 'Carlos Pereira', '55544433322', 'Matemática', 50, 2, 'PROFESSOR');
