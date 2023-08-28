-- Desativar verificações de chave estrangeira
SET CONSTRAINTS ALL DEFERRED;

-- Limpar tabelas
DELETE FROM voto;
DELETE FROM sessao_votacao;
DELETE FROM pauta;
DELETE FROM associado;

-- Reiniciar sequências (para PostgreSQL)
ALTER SEQUENCE voto_id_seq RESTART WITH 1;
ALTER SEQUENCE sessao_votacao_id_seq RESTART WITH 1;
ALTER SEQUENCE pauta_id_seq RESTART WITH 1;
ALTER SEQUENCE associado_id_seq RESTART WITH 1;

-- Inserir dados de exemplo
INSERT INTO associado (id, nome, cpf) VALUES (1, 'Luiz da silva', '12345678901');
INSERT INTO associado (id, nome, cpf) VALUES (2, 'João da Silva', '12345678902');
INSERT INTO associado (id, nome, cpf) VALUES (3, 'Prdro Vargas', '12345678903');
INSERT INTO associado (id, nome, cpf) VALUES (4, 'Anna Clara', '12345678904');

INSERT INTO pauta (id, tema, descricao) VALUES (1, 'teste', 'teste');

INSERT INTO sessao_votacao (id, id_pauta, duracao, status) VALUES (1, 1, 1, 'fechada');

INSERT INTO voto (id, id_sessao, id_associado, valor) VALUES (1, 1, 1, 'Sim');
INSERT INTO voto (id, id_sessao, id_associado, valor) VALUES (2, 1, 1, 'Sim');
INSERT INTO voto (id, id_sessao, id_associado, valor) VALUES (3, 1, 1, 'Sim');
INSERT INTO voto (id, id_sessao, id_associado, valor) VALUES (4, 1, 1, 'Não');

ALTER SEQUENCE associado_id_seq RESTART WITH 5;
ALTER SEQUENCE pauta_id_seq RESTART WITH 2;
ALTER SEQUENCE sessao_votacao_id_seq RESTART WITH 2;
ALTER SEQUENCE voto_id_seq RESTART WITH 5;

-- Ativar verificações de chave estrangeira
SET CONSTRAINTS ALL IMMEDIATE;
