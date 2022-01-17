CREATE TABLE group_expense
(
    id         SERIAL PRIMARY KEY,
    expense_id INTEGER
        CONSTRAINT fk_expense_id
            REFERENCES expense (id),
    group_id   INTEGER
        CONSTRAINT fk_group_id
            REFERENCES "group" (id)
);