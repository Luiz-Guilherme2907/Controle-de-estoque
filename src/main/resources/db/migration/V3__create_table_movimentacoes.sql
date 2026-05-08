CREATE TABLE movimentacoes (
    id          BIGSERIAL   PRIMARY KEY,
    produto_id  BIGINT      NOT NULL REFERENCES produtos(id),
    tipo        VARCHAR(10) NOT NULL,
    quantidade  INT         NOT NULL,
    observacao  TEXT,
    criado_em   TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_movimentacoes_produto_id ON movimentacoes(produto_id);
