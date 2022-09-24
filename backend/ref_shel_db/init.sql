CREATE TABLE Rooms (
    id int primary key,
    address varchar not null ,
    latitude varchar not null ,
    longitude varchar not null ,
    price int,
    has_kitchen bool,
    has_bathroom bool,
    description varchar
);

CREATE TABLE IF NOT EXISTS Role (
    id int primary key not null,
    name varchar
);

CREATE TABLE IF NOT EXISTS Users (
    id int primary key not null,
    username varchar,
    name varchar,
    password varchar,
    email varchar,
    phone varchar
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id int not null,
    roles_id int not null,
    foreign key (roles_id) references Role(id),
    foreign key (user_id) references Users(id)
);

INSERT INTO Users (id, name, username, password, email, phone) VALUES
(1, 'Ivan Petrov', 'akrik', 'akrik1234', 'akrik@gmail.com', '89067132145'),
(2, 'Maria Phil', 'pavel', 'qwertuyop', 'mari@gmail.com', '89053411190'),
(3, 'Regina Khamizna', 'ivan',  '5234123', 'regian123@gmail.com', '89032123562'),
(4, 'Alexey Kekov', 'pidorka123', 'petrovich', 'pidorka228@gmail.com', '89046112352');

INSERT INTO Role(id, name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_MANAGER'),
(3, 'ROLE_ADMIN'),
(4, 'ROLE_SUPER_ADMIN');

INSERT INTO user_roles(user_id, roles_id) VALUES
(1, 1),
(2, 2),
(3, 4),
(4, 3);


INSERT INTO user_roles(user_id, roles_id) VALUES
(4, 2),
(4, 3);


DROP TABLE IF EXISTS user_roles;
drop table if exists role;
drop table if exists users