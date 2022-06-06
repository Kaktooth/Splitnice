CREATE TABLE users
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR UNIQUE NOT NULL,
    password     VARCHAR        NOT NULL,
    enabled      BOOLEAN        NOT NULL,
    phone_number VARCHAR        NOT NULL
);