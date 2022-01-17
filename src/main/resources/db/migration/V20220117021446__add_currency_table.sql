CREATE TABLE currency
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR,
    exchange_rate DECIMAL,
    change_time   TIMESTAMP
);
