CREATE TABLE account_contact
(
    id               SERIAL PRIMARY KEY,
    contact_username VARCHAR,
    contact_id       INTEGER
        CONSTRAINT fk_contact_id REFERENCES contact (id),
    account_id       INTEGER
        CONSTRAINT fk_account_id REFERENCES account (id)
);