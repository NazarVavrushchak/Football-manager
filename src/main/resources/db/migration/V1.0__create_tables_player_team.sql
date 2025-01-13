CREATE TABLE teams
(
    id                    SERIAL PRIMARY KEY,
    name                  VARCHAR(100)   NOT NULL UNIQUE,
    commission_percentage NUMERIC(5, 2)  NOT NULL CHECK (commission_percentage >= 0 AND commission_percentage <= 10),
    balance               NUMERIC(15, 2) NOT NULL CHECK (balance >= 0)
);

CREATE TABLE players
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(100)                        NOT NULL,
    surname           VARCHAR(100)                        NOT NULL,
    age               INT CHECK (age >= 16 AND age <= 50) NOT NULL,
    experience_months INT CHECK (experience_months >= 0)  NOT NULL,
    team_id           INT                                 REFERENCES teams (id) ON DELETE SET NULL,
    is_in_team         BOOLEAN                             NOT NULL
);

INSERT INTO teams (name, commission_percentage, balance)
VALUES ('Sokil', 5.00, 1000000.00),
       ('Karpaty', 2.50, 750000.00);

INSERT INTO players (name, surname, age, experience_months, team_id, is_in_team)
VALUES ('Nazar', 'Vavrushchak', 19, 2, 1, true),
       ('Ivan', 'Kopach', 30, 60, 1, false),
       ('Oleg', 'Bobmardir', 22, 12, 2, true);