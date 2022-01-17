CREATE TABLE individual_expense
(
    id         SERIAL PRIMARY KEY,
    expense_id INTEGER
        CONSTRAINT fk_expense_id
            REFERENCES expense (id),
    user_id    INTEGER
        CONSTRAINT fk_account_id
            REFERENCES account (id)
);