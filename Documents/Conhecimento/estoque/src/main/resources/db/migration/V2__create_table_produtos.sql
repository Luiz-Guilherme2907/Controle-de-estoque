CREATE TABLE produtos (
    id          BIGSERIAL       PRIMARY KEY,
    nome        VARCHAR(255)    NOT NULL,
    descricao   TEXT,
    quantidade  INT             NOT NULL DEFAULT 0,
    preco       NUMERIC(10, 2)  NOT NULL,
    ativo       BOOLEAN         NOT NULL DEFAULT TRUE
);
