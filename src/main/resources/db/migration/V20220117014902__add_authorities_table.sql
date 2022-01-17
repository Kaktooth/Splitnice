CREATE TABLE authorities
(
    id INTEGER NOT NULL,
    username  VARCHAR NOT NULL,
    authority INTEGER NOT NULL
        REFERENCES user_authorities (id),
    CONSTRAINT fk_user_authorities
        FOREIGN KEY (id) REFERENCES users (id)
);