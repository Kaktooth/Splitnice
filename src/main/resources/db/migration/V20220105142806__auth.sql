CREATE TABLE account_role
(
    id   SERIAL PRIMARY KEY,
    role VARCHAR
);

ALTER TABLE account
    ADD password VARCHAR,
    ADD enabled  boolean not null,
    ADD role     INTEGER not null
        constraint fk_account_role
            references account_role (id);


create table authorities
(
    id        INTEGER
        constraint fk_authorities_account
            references account (id),
    username  VARCHAR     not null,
    authority VARCHAR(50) not null
);

create unique index ix_auth_account on authorities (username, authority);

create table persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) primary key,
    token     varchar(64) not null,
    last_used timestamp   not null
);