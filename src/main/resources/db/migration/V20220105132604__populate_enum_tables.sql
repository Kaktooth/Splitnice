INSERT INTO currency (title, exchange_rate, change_time)
    VALUES ('USD', 1, current_timestamp), ('EUR', 1.13, current_timestamp), ('UAH', 0.037, current_timestamp);

INSERT INTO operation_type (type) VALUES ('expense'), ('payment');

INSERT INTO group_role (title) VALUES ('admin'), ('user');