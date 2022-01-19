CREATE TABLE account_group
(
    id          SERIAL PRIMARY KEY,
    role_id     INTEGER
        CONSTRAINT fk_group_role REFERENCES group_role (id),
    added_by_id INTEGER
        CONSTRAINT fk_added_by REFERENCES account (id),
    group_id    INTEGER
        CONSTRAINT fk_group REFERENCES "group" (id),
    account_id  INTEGER
        CONSTRAINT fk_account REFERENCES account (id)
);