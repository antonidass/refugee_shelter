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
    name        varchar not null,
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


INSERT INTO Rooms(name, address, latitude, longitude, price, beds, people, description, image_url, owner_id)
VALUES ('Best option on for you!', 'Moscow, Borovay St. 8, 123', 55.749, 37.636, 1200, 1, 4,
        'This is good rooms for refugees.... and jdkal cjdka lekdjf ladksf j fksdj lkajds lfjld jfkj klfjds alkjlfk jdslkj fls',
        'https://testimages.com/32', 1),
       ('Good Room on Chertanovo!', 'Moscow, Chertanovo St. 1, 12', 55.5, 37.4, 1300, 2, 1,
        'Really good refugees.... fsdk fsda fkas dkfkd ak kasld kcaksdcj dsac sklnc klj klajsd kjsakl kcna sk',
        'https://testimages.com/12',
        2),
       ('Very very very for u', 'Moscow, Ivanov St. 5, 253', 54.9, 36.9, 200, 3, 5,
        'Super offer for  sdfkjsadjf ksaj kjs fdkl jfklsaj fklj ksdlj flksja flkjsaklfdj klsjd fkjs lkjs dljflsdk jlks djlkf jsldkjf lksadgays rooms for refugees....',
        'https://testimages.com/54', 3),
       ('I think you can afford it!', 'Moscow, Lenina St. 1, 4', 56.6, 37.8, 700, 4, 2,
        'Only for Ukrainian  sfd fa dfaf s fd af aa f fasd ffor refugees....',
        'https://testimages.com/1', 4),
       ('Really good room!', 'Moscow, Pesina St. 2, 425', 56.3, 38.1, 900, 6, 3,
        'Easy  rooa da dsfas asd fas f sadf asd fasdf asdf d a fdf a f adfms for refugees....',
        'https://testimages.com/5', 4);

INSERT INTO Rooms(name, address, latitude, longitude, price, beds, people, description, image_url, owner_id)
VALUES ('Good room On Borovaya street...', 'Moscow, Borovay St. 8, 123', 55.749, 40.636, 1200, 1, 4,
        'This is good rooms for refugees.... and jdkal cjdka lekdjf ladksf j fksdj lkajds lfjld jfkj klfjds alkjlfk jdslkj fls',
        'https://testimages.com/32', 5),
       ('This is for u!', 'Moscow, Chertanovo St. 1, 12', 55.5, 40.4, 1300, 2, 1,
        'Really good refugees.... fsdk fsda fkas dkfkd ak kasld kcaksdcj dsac sklnc klj klajsd kjsakl kcna sk',
        'https://testimages.com/12',
        5),
       ('The Best!', 'Moscow, Ivanov St. 5, 253', 54.9, 40.9, 200, 3, 5,
        'Super offer for  sdfkjsadjf ksaj kjs fdkl jfklsaj fklj ksdlj flksja flkjsaklfdj klsjd fkjs lkjs dljflsdk jlks djlkf jsldkjf lksadgays rooms for refugees....',
        'https://testimages.com/54', 5),
       ('I know you can afford it!', 'Moscow, Lenina St. 1, 4', 56.6, 36.8, 700, 4, 2,
        'Only for Ukrainian  sfd fa dfaf s fd af aa f fasd ffor refugees....',
        'https://testimages.com/1', 5),
       ('Thanks for watching!', 'Moscow, Pesina St. 2, 425', 56.3, 39.1, 900, 6, 3,
        'Easy  rooa da dsfas asd fas f sadf asd fasdf asdf d a fdf a f adfms for refugees....',
        'https://testimages.com/5', 5);



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
