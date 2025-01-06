--liquibase formatted sql

-- changeset nazar:insert-teams-data
INSERT INTO teams (name, balance, commission_percentage)
VALUES
    ('Liverpool', 1000000, 9),
    ('Arsenal', 1200000, 9),
    ('Nottingham Forest', 400000, 6),
    ('Chelsea', 2000000, 9),
    ('Newcastle', 600000, 7),
    ('Manchester City', 2202000, 10),
    ('Bournemouth', 402600, 6),
    ('Aston Villa', 806300, 7),
    ('Fulham', 553000, 5),
    ('Tottenham', 1156200, 8);

-- changeset nazar:insert-players-data
INSERT INTO players (first_name, last_name, birth_date, experience_months, team_id)
VALUES
    ('Mohamed', 'Salah', '1992-07-15', 30, 1),
    ('William', 'Saliba', '1995-02-20', 15, 2),
    ('Matz', 'Sels', '1989-08-25', 26, 3),
    ('Cole', 'Palmer', '1996-06-30', 24, 4),
    ('Alexander', 'Isak', '1996-11-10', 12, 5),
    ('Erling', 'Haaland', '1998-10-17', 13, 6),
    ('Illia', 'Zabarnyi', '1999-12-20', 15, 7),
    ('Jhon', 'Duran', '1996-07-10', 17, 8),
    ('Steven', 'Benda', '1999-05-12', 6, 9),
    ('Timo', 'Werner', '1997-03-02', 10, 10);
