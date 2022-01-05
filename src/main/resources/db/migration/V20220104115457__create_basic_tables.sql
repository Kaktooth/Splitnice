CREATE TABLE account
(
    id        SERIAL PRIMARY KEY,
    username  VARCHAR,
    amount    DECIMAL,
    email     VARCHAR,
    phone     VARCHAR,
    signed_up BOOLEAN
);

CREATE TABLE "group"
(
    id     SERIAL PRIMARY KEY,
    title  VARCHAR,
    amount DECIMAL
);

CREATE TABLE group_role
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR
);

CREATE TABLE group_account
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

CREATE TABLE currency
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR,
    exchange_rate DECIMAL,
    change_time   TIMESTAMP
);

CREATE TABLE operation_type
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR
);

CREATE TABLE financial_operation
(
    id                SERIAL PRIMARY KEY,
    amount            DECIMAL,
    creation_date     TIMESTAMP,
    currency_id       INTEGER
        CONSTRAINT fk_currency_id
            REFERENCES currency (id),
    operation_type_id INTEGER
        CONSTRAINT fk_operation_type_id
            REFERENCES operation_type (id)

);

CREATE TABLE transaction
(
    id           SERIAL PRIMARY KEY,
    amount       DECIMAL,
    lander_id    INTEGER
        CONSTRAINT fk_lander_id
            REFERENCES account (id),
    receiver_id  INTEGER
        CONSTRAINT fk_receiver_id
            REFERENCES account (id),
    operation_id INTEGER
        CONSTRAINT fk_operation_id
            REFERENCES financial_operation (id)
)