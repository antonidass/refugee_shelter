CREATE TABLE IF NOT EXISTS Role
(
    id   int primary key not null,
    name varchar
);

CREATE TABLE IF NOT EXISTS Users
(
    id       int primary key not null,
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
    id           serial primary key,
    address      varchar not null,
    latitude     float not null,
    longitude    float not null,
    price        int,
    has_kitchen  bool,
    has_bathroom bool,
    description  varchar,
    owner_id     int,
    foreign key (owner_id) references Users (id)
);

CREATE TABLE IF NOT EXISTS Reservations
(
    id         serial primary key,
    start_date date,
    end_date   date,
    room_id    int,
    user_id    int,
    foreign key (room_id) references Rooms (id),
    foreign key (user_id) references Users (id)
);


INSERT INTO Users (id, name, username, password, email, phone)
VALUES (1, 'Ivan Petrov', 'akrik', 'akrik1234', 'akrik@gmail.com', '89067132145'),
       (2, 'Maria Phil', 'pavel', 'qwertuyop', 'mari@gmail.com', '89053411190'),
       (3, 'Regina Khamizna', 'ivan', '5234123', 'regian123@gmail.com', '89032123562'),
       (4, 'Alexey Kekov', 'pidorka123', 'petrovich', 'pidorka228@gmail.com', '89046112352');

INSERT INTO Role(id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_MANAGER'),
       (3, 'ROLE_ADMIN'),
       (4, 'ROLE_SUPER_ADMIN');

INSERT INTO user_roles(user_id, roles_id)
VALUES (1, 1),
       (2, 2),
       (3, 4),
       (4, 3);


INSERT INTO user_roles(user_id, roles_id)
VALUES (4, 2),
       (4, 3);


INSERT INTO Rooms(id, address, latitude, longitude, price, has_kitchen, has_bathroom, description, owner_id)
VALUES (1, 'Moscow, Borovay St. 8, 123', 0, 0, 1200, true, false, 'This is good rooms for refugees....', 1),
       (2, 'Moscow, Chertanovo St. 1, 12', 0, 0, 1300, true, true, 'Really good refugees....', 2),
       (3, 'Moscow, Ivanov St. 5, 253', 0, 0, 200, false, false, 'Super offer for gays rooms for refugees....', 3),
       (4, 'Moscow, Lenina St. 1, 4', 0, 0, 700, false, true, 'Only for Ukrainian for refugees....', 4),
       (5, 'Moscow, Pesina St. 2, 425', 0, 0, 900, false, false, 'Easy  rooms for refugees....', 4);


INSERT INTO Reservations(id, start_date, end_date, room_id, user_id)
VALUES (1, '2022-04-23', '2022-05-23', 5, 3),
       (2, '2022-05-13', '2022-08-10', 6, 2),
       (3, '2022-03-13', '2022-07-05', 7, 3),
       (4, '2022-02-22', '2022-05-10', 8, 1);

DROP TABLE IF EXISTS user_roles;
drop table if exists role;
drop table if exists reservations;
drop table if exists rooms;
drop table if exists users;
