CREATE SEQUENCE IF NOT EXISTS usersSerial START 1;
CREATE SEQUENCE IF NOT EXISTS rolesSerial START 1;
CREATE SEQUENCE IF NOT EXISTS roomsSerial START 1;
CREATE SEQUENCE IF NOT EXISTS reservationsSerial START 1;

CREATE TABLE IF NOT EXISTS Role
(
    id   int default nextval('rolesSerial') primary key,
    name varchar
);

CREATE TABLE IF NOT EXISTS Users
(
    id       int default nextval('usersSerial') primary key,
    username varchar,
    name     varchar,
    password varchar,
    email    varchar,
    phone    varchar
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id  int not null,
    roles_id int not null,
    foreign key (roles_id) references Role (id),
    foreign key (user_id) references Users (id)
);

CREATE TABLE IF NOT EXISTS Rooms
(
    id           int default nextval('roomsSerial') primary key,
    address      varchar not null,
    latitude     float   not null,
    longitude    float   not null,
    price        int,
    beds  int,
    people int,
    description  varchar,
    image_url    varchar,
    owner_id     int,
    foreign key (owner_id) references Users (id)
);

CREATE TABLE IF NOT EXISTS Reservations
(
    id         int default nextval('reservationsSerial') primary key,
    start_date date,
    end_date   date,
    room_id    int,
    user_id    int,
    foreign key (room_id) references Rooms (id),
    foreign key (user_id) references Users (id)
);



INSERT INTO Users (name, username, password, email, phone)
VALUES ('Ivan Petrov', 'login', '$2y$10$6nUrTeU.wQ9Qe6md7bzUaOYcQ9nh1xRBmeNjlQ8SId5.JjH9yOnNi', 'akr@gmail.com',
        '89067132145'),
       ('Maria Phil', 'maria', 'qwertuyop', 'mari@gmail.com', '89053411190'),
       ('Admin adminov', 'ivan', '5234123', 'regian123@gmail.com', '89032123562'),
       ('Alexey Kekov', 'kek123', 'petrovich', 'kekes@gmail.com', '89046112352');

INSERT INTO Role(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');


INSERT INTO user_roles(user_id, roles_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);

INSERT INTO user_roles(user_id, roles_id)
VALUES (1, 2);


INSERT INTO Rooms(address, latitude, longitude, price, beds, people, description, image_url, owner_id)
VALUES ('Moscow, Borovay St. 8, 123', 0, 0, 1200, 1, 4, 'This is good rooms for refugees....',
        'https://testimages.com/32', 1),
       ('Moscow, Chertanovo St. 1, 12', 0, 0, 1300, 2, 1, 'Really good refugees....', 'https://testimages.com/12',
        2),
       ('Moscow, Ivanov St. 5, 253', 0, 0, 200, 3, 5, 'Super offer for gays rooms for refugees....',
        'https://testimages.com/54', 3),
       ('Moscow, Lenina St. 1, 4', 0, 0, 700, 4, 2, 'Only for Ukrainian for refugees....',
        'https://testimages.com/1', 4),
       ('Moscow, Pesina St. 2, 425', 0, 0, 900, 6, 3, 'Easy  rooms for refugees....',
        'https://testimages.com/5', 4);


INSERT INTO Reservations(start_date, end_date, room_id, user_id)
VALUES ('2022-04-23', '2022-05-23', 5, 3),
       ('2022-05-13', '2022-08-10', 5, 2),
       ('2022-03-13', '2022-07-05', 4, 3),
       ('2022-02-22', '2022-05-10', 3, 1);

DROP TABLE IF EXISTS user_roles;
drop table if exists role;
drop table if exists reservations;
drop table if exists rooms;
drop table if exists users;
DROP sequence if exists rolesSerial;
DROP sequence if exists reservationsSerial;
DROP sequence if exists roomsSerial;
DROP sequence if exists usersSerial;
