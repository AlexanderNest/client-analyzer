CREATE TABLE communication_type
(
    id    int auto_increment PRIMARY KEY,
    name  varchar(255) NOT NULL
);

CREATE TABLE type_of_change
(
    id    int auto_increment PRIMARY KEY,
    name  varchar(255) NOT NULL
);

CREATE TABLE client
(
    id    int auto_increment PRIMARY KEY,
    name  varchar(255) NOT NULL,
    cost_per_hour int NOT NULL,
    count_of_hours_pr_week float,
    count_of_meetings_pr_week int,
    active bit (1),
    date_of_beginning date not null
);

CREATE TABLE schedule_change
(
    id    int auto_increment PRIMARY KEY,
    client_id int NOT NULL,
    date date NOT NULL,
    new_date date,
    planned bit(1),
    type_of_change_id int NOT NULL
);

CREATE TABLE communication
(
    client_id int NOT NULL PRIMARY KEY,
    communication_type_id int NOT NULL,
    contact varchar(255) NOT NULL
);

INSERT INTO communication_type (name) VALUES
                                          ('SKYPE'),
                                          ('TELEGRAM'),
                                          ('WHATSAPP'),
                                          ('PHONE');

INSERT INTO type_of_change (name) VALUES
                                      ('SHIFTED'),
                                      ('CANCELLED');