CREATE TABLE voto (
    id BIGSERIAL PRIMARY KEY,
    id_sessao BIGINT NOT NULL,
    id_associado BIGINT NOT NULL,
    valor VARCHAR(3) CHECK (valor IN ('Sim', 'Não')) NOT NULL,
    data_voto TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_sessao) REFERENCES sessao_votacao(id),
    FOREIGN KEY (id_associado) REFERENCES associado(id)
);
