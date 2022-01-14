CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    enabled  BOOLEAN NOT NULL,
    phone_number VARCHAR NOT NULL
);

CREATE TABLE user_authorities
(
    id          SERIAL PRIMARY KEY,
    authorities VARCHAR NOT NULL
);

INSERT INTO user_authorities
VALUES (0, 'USER'),
       (1, 'ADMIN');

CREATE TABLE authorities
(
    id INTEGER NOT NULL,
    username  VARCHAR NOT NULL,
    authority INTEGER NOT NULL
        REFERENCES user_authorities (id),
    CONSTRAINT fk_user_authorities
        FOREIGN KEY (id)
            REFERENCES users (id)
);
CREATE UNIQUE INDEX ix_auth_account ON authorities (id, authority);

CREATE TABLE persistent_logins
(
    username  VARCHAR   NOT NULL,
    series    VARCHAR PRIMARY KEY,
    token     VARCHAR   NOT NULL,
    last_used TIMESTAMP NOT NULL
);