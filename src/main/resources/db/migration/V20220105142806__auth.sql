CREATE TABLE users
(
    username VARCHAR NOT NULL PRIMARY KEY,
    password VARCHAR NOT NULL,
    enabled  BOOLEAN NOT NULL
);

CREATE TABLE user_authorities
(
    id          SERIAL PRIMARY KEY,
    authorities VARCHAR NOT NULL
);

CREATE TABLE authorities
(
    username  VARCHAR NOT NULL,
    authority INTEGER NOT NULL
        REFERENCES user_authorities (id),
    CONSTRAINT fk_user_authorities
        FOREIGN KEY (username)
            REFERENCES users (username)
);
CREATE UNIQUE INDEX ix_auth_account ON authorities (username, authority);

CREATE TABLE persistent_logins
(
    username  VARCHAR   NOT NULL,
    series    VARCHAR PRIMARY KEY,
    token     VARCHAR   NOT NULL,
    last_used TIMESTAMP NOT NULL
);