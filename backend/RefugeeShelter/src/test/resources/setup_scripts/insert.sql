INSERT INTO Users (name, username, password, email, phone)
VALUES ('Ivan Petrov', 'login', '$2y$10$EoGzQETN8aV8cPwIvH4el.vdOQWzLG2YchwZZoyscnHNZZelJGr4C', 'akr@gmail.com',
        '89067132145'),
       ('Maria Phil', 'maria', '$2y$10$EoGzQETN8aV8cPwIvH4el.vdOQWzLG2YchwZZoyscnHNZZelJGr4C', 'mari@gmail.com',
        '89053411190'),
       ('Admin adminov', 'ivan', '$2y$10$EoGzQETN8aV8cPwIvH4el.vdOQWzLG2YchwZZoyscnHNZZelJGr4C', 'regian123@gmail.com',
        '89032123562'),
       ('Alexey Kekov', 'kek123', '$2y$10$EoGzQETN8aV8cPwIvH4el.vdOQWzLG2YchwZZoyscnHNZZelJGr4C', 'kekes@gmail.com',
        '89046112352');

INSERT INTO Role(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');


INSERT INTO user_roles(user_id, roles_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);


INSERT INTO Rooms(address, latitude, longitude, price, beds, people, description, image_url, owner_id)
VALUES ('Moscow, Borovay St. 8, 123', 50, 0, 1200, 1, 4, 'This is good rooms for refugees....',
        'https://testimages.com/32', 1),
       ('Moscow, Chertanovo St. 1, 12', 0, 50, 1300, 2, 1, 'Really good refugees....', 'https://testimages.com/12',
        2),
       ('Moscow, Ivanov St. 5, 253', 30, 30, 200, 3, 5, 'Super offer for gays rooms for refugees....',
        'https://testimages.com/54', 3),
       ('Moscow, Lenina St. 1, 4', 50, 40, 700, 4, 2, 'Only for Ukrainian for refugees....',
        'https://testimages.com/1', 4),
       ('Moscow, Pesina St. 2, 425', 35, 32, 900, 6, 3, 'Easy  rooms for refugees....',
        'https://testimages.com/5', 4);


INSERT INTO Reservations(start_date, end_date, room_id, user_id)
VALUES ('2022-04-23', '2022-05-23', 5, 3),
       ('2022-05-13', '2022-08-10', 5, 2),
       ('2022-03-13', '2022-07-05', 4, 3),
       ('2022-02-22', '2022-05-10', 1, 2);