CREATE TABLE account
(
    id          SERIAL PRIMARY KEY,
    username    VARCHAR,
    amount      DECIMAL,
    user_id     INTEGER
        CONSTRAINT fk_user_id
            REFERENCES users (id),
    currency_id INTEGER
        CONSTRAINT fk_currency_id
            REFERENCES currency (id)
);