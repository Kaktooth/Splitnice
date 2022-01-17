CREATE TABLE "group"
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR,
    creator_id INTEGER
        CONSTRAINT fk_creator REFERENCES account (id)
);