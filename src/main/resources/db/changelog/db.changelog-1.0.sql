--liquibase formatted sql

-- changeset nazar:create-teams-table
CREATE TABLE teams
(
    id                    SERIAL PRIMARY KEY,
    name                  VARCHAR(255),
    balance               NUMERIC(10, 2),
    commission_percentage NUMERIC(5, 2)
);

-- changeset nazar:create-player-table
CREATE TABLE players
(
    id                SERIAL PRIMARY KEY,
    first_name        VARCHAR(255),
    last_name         VARCHAR(255),
    birth_date        DATE,
    experience_months INTEGER,
    team_id           INTEGER,
    CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE SET NULL
);
