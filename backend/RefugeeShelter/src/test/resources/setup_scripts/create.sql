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
    id          int default nextval('roomsSerial') primary key,
    address     varchar not null,
    latitude    float   not null,
    longitude   float   not null,
    price       int,
    beds        int,
    people      int,
    description varchar,
    image_url   varchar,
    owner_id    int,
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
