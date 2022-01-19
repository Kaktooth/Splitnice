CREATE TABLE currency
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR,
    exchange_rate DECIMAL,
    change_time   TIMESTAMP
);

CREATE TABLE expense_type
(
    id   SERIAL PRIMARY KEY,
    title VARCHAR
);

CREATE TABLE group_role
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR
);

CREATE TABLE account
(
    id        SERIAL PRIMARY KEY,
    username  VARCHAR,
    email     VARCHAR,
    phone     VARCHAR,
    amount    DECIMAL,
    currency_id INTEGER
        CONSTRAINT fk_currency_id
            REFERENCES currency (id)
);

CREATE TABLE "group"
(
    id     SERIAL PRIMARY KEY,
    title  VARCHAR,
    creator_id INTEGER
        CONSTRAINT fk_creator
            REFERENCES account (id)
);

CREATE TABLE account_group
(
    id          SERIAL PRIMARY KEY,
    role_id     INTEGER
        CONSTRAINT fk_group_role
            REFERENCES group_role (id),
    added_by_id INTEGER
        CONSTRAINT fk_added_by
            REFERENCES account (id),
    group_id    INTEGER
        CONSTRAINT fk_group
            REFERENCES "group" (id),
    account_id  INTEGER
        CONSTRAINT fk_account
            REFERENCES account (id)
);

CREATE TABLE expense
(
    id              SERIAL PRIMARY KEY,
    amount          DECIMAL,
    creation_date   TIMESTAMP,
    currency_id     INTEGER
        CONSTRAINT fk_currency_id
            REFERENCES currency (id),
    expense_type_id INTEGER
        CONSTRAINT fk_expense_type_id
            REFERENCES expense_type (id)
);

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