CREATE TABLE transaction
(
    id          SERIAL PRIMARY KEY,
    amount      DECIMAL,
    currency_id INTEGER
        CONSTRAINT fk_currency_id
            REFERENCES currency (id),
    lander_id   INTEGER
        CONSTRAINT fk_lander_id
            REFERENCES account (id),
    receiver_id INTEGER
        CONSTRAINT fk_receiver_id
            REFERENCES account (id),
    expense_id  INTEGER
        CONSTRAINT fk_expense_id
            REFERENCES expense (id)
)