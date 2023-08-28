CREATE TABLE sessao_votacao (
    id BIGSERIAL PRIMARY KEY,
    id_pauta BIGINT NOT NULL,
    data_inicio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    duracao INTEGER NOT NULL DEFAULT 1,
    status VARCHAR(255) NOT NULL CHECK (status IN ('aberta', 'fechada')),
    FOREIGN KEY (id_pauta) REFERENCES pauta(id)
);
