INSERT INTO users(username, password, enabled, phone_number)
VALUES ('34n@gmail.com', '$2a$12$wY.oyPSrd9nWz1eVeC3NROpcaRAiC/dAsLwla/Q1kR1oismigw5Qi', true, '380992393299');
INSERT INTO users(username, password, enabled, phone_number)
VALUES ('skyrim@gmail.com', '$2a$12$XHSSJaDM7q7p/ZUj4r2cWucri8LOh0BYkuV9NY3OEzWlPwO0ADKYC', true, '380992333269');

INSERT INTO authorities(id, username, authority)
VALUES (1, '34n@gmail.com', 0),
       (2, 'skyrim@gmail.com', 0);