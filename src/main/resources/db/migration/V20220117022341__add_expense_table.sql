CREATE TABLE expense
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR,
    amount        DECIMAL,
    creation_date TIMESTAMP,
    author_id     INTEGER
        CONSTRAINT fk_author_id REFERENCES account (id),
    currency_id   INTEGER
        CONSTRAINT fk_currency_id REFERENCES currency (id)
);