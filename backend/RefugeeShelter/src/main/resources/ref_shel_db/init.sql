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
VALUES ('Ivan', 'ivan', '$2a$12$RaoXUh26FKoiqNubgCH5vOZagc/QFuMiO8i4iQmGY9vL1M6Yez/wK', 'akr@gmail.com', '89067132145'),
       ('Maria', 'maria', '$2a$12$RaoXUh26FKoiqNubgCH5vOZagc/QFuMiO8i4iQmGY9vL1M6Yez/wK', 'mari@gmail.com', '89053411190'),
       ('Petr', 'petr', '$2a$12$RaoXUh26FKoiqNubgCH5vOZagc/QFuMiO8i4iQmGY9vL1M6Yez/wK', 'regian123@gmail.com', '89032123562'),
       ('Alexey', 'alex', '$2a$12$RaoXUh26FKoiqNubgCH5vOZagc/QFuMiO8i4iQmGY9vL1M6Yez/wK', 'aklex@gmail.com', '89046112354'),
       ('anton', 'antonio', '$2a$12$RaoXUh26FKoiqNubgCH5vOZagc/QFuMiO8i4iQmGY9vL1M6Yez/wK', 'antonio@gmail.com', '89046112355'),
       ('anton', 'antonidas', '$2a$12$RaoXUh26FKoiqNubgCH5vOZagc/QFuMiO8i4iQmGY9vL1M6Yez/wK', 'antonida@gmail.com', '89046112351'),
       ('pavel', 'pavel', '$2a$12$RaoXUh26FKoiqNubgCH5vOZagc/QFuMiO8i4iQmGY9vL1M6Yez/wK', 'pavel@gmail.com', '89546112351');


INSERT INTO Role(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');


INSERT INTO user_roles(user_id, roles_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1);
--
-- INSERT INTO user_roles(user_id, roles_id)
-- VALUES (1, 2);


INSERT INTO Rooms(name, address, latitude, longitude, price, beds, people, description, image_url, owner_id)
VALUES ('The Other House South Kensington', 'Moscow, Borovay St. 8, 123', 55.749, 37.636, 1200, 1, 4,
        'Located 9 km from Milton Keynes Bowl, Walker Avenue Apartments with Parking by 360 Midlands provides accommodation with free WiFi and free private parking.',
        'https://testimages.com/32', 1),
       ('Wilde Aparthotels Manchester St. Peters Square', 'Moscow, Chertanovo St. 1, 12', 55.5, 37.4, 1300, 2, 1,
        'Situated in Bathford, Kings Arms Duplex features free WiFi, and guests can enjoy a garden, a shared lounge and a terrace.',
        'https://testimages.com/12',
        2),
       ('Wilde Aparthotels by Staycity London Paddington', 'Moscow, Ivanov St. 5, 253', 54.9, 36.9, 200, 3, 5,
        'Excellent 2-Bed Apartment in Colindale London is set in Hendon, 4.4 km from Edgware, 6 km from Preston Road, as well as 6.8 km from Wembley Arena.',
        'https://testimages.com/54', 3),
       ('Wilde Aparthotels by Staycity London Aldgate Tower Bridge', 'Moscow, Lenina St. 1, 4', 56.6, 37.8, 700, 4, 2,
        'Set in Southwold, just 100 metres from Southwold Pier Beach, The Shiny Pebble, 5* ground floor.Coastal retreat. Offers beachfront accommodation with free WiFi.',
        'https://testimages.com/1', 4),
       ('CitySuites Two Aparthotel', 'Moscow, Pesina St. 2, 425', 56.3, 38.1, 900, 3, 3,
        'HU-Thirteen Apt Two- Sleeps 4 is situated in Hessle, 7.9 km from Hull Arena, 8.7 km from Hull Train Station, and 9.2 km from Hull New Theatre.',
        'https://testimages.com/5', 4),
       ('Bermonds Locke', '1st Krasnogvardeysky Proezd 21, Bldg 1, Entrance B,', 55.749, 40.636, 1200, 1, 4,
        'Set in Liverpool within a 4-minute walk of Royal Court Theatre, FM Living features accommodation with a flat-screen TV and a kitchen. There is also an oven, toaster and a kettle.',
        'https://testimages.com/32', 5),
       ('Design Suites Lytham', '8 Novinsky Boulevard, Moscow,', 55.5, 40.4, 1300, 2, 1,
        'Set in Cockwood, 2 km from Red Rock Beach and 2.8 km from Dawlish Beach, Bosuns Reach provides accommodation with amenities such as free WiFi and a flat-screen TV.',
        'https://testimages.com/12',
        5),
       ('Quest Liverpool City Centre ', 'Sadovnicheskaya Ulitsa 82, building 2, Moscow,', 54.9, 40.9, 200, 3, 5,
        'Situated just outside the city of Cambridge, this Victorian house offers spacious accommodation with an en suite bathroom, King size bed, and a flat-screen TV.',
        'https://testimages.com/54', 5),
       ('Whitworth Locke ', 'Moscow, Lenina St. 1, 4', 56.6, 36.8, 700, 4, 2,
        'Leven Locke Spacious Apartment is an accommodation set in Saltburn-by-the-Sea, 21 km from Middlesbrough Cathedral and 33 km from Whitby Abbey.',
        'https://testimages.com/1', 6),
       ('Native Bankside', 'Bolshoy Boulevard 40, Skolkovo Innovation Center, Moscow,', 53.2, 40.1, 900, 4, 3,
        'Situated in Kingsthorpe, 20 km from Kelmarsh Hall and 37 km from Milton Keynes Bowl, Central Northampton House Walk to Town Centre provides accommodation with amenities such as free WiFi and a...',
        'https://testimages.com/5', 3),
       ('The Gresham Aparthotel', 'Timura Frunze 11/13, Moscow', 56.6, 39.3, 900, 5, 3,
        'Providing city views and free WiFi, iStay Liverpool Water Street provides accommodation well set in the centre of Liverpool, within a short distance of Western Approaches Museum, Liverpool ONE and...',
        'https://testimages.com/5', 2),
       ('Seaview Luxury Apartments', 'Leningradsky Prospekt 39, bld. 79, Moscow', 55.1, 43.4, 900, 3, 2,
        'Southsea Retreat has garden views, free WiFi and free private parking, set in Southsea, 700 metres from Eastney Beach.',
        'https://testimages.com/5', 1),
       ('Namastay-Central Glastonbury', 'Moscow, Petrova St. 11, 225', 57.2, 40.0, 322, 5, 1,
        'Set in Ilfracombe and only 600 metres from Wildersmouth beach, The Lookout offers accommodation with sea views, free WiFi and free private parking.',
        'https://testimages.com/5', 4),
       ('Stayo Kew Gardens', 'Moscow, Kirova St. 1, 1425', 58.3, 39.2, 544, 3, 1,
        'Located 17 km from King George''s Hall and 21 km from Heaton Park, Moat House provides accommodation in Rawtenstall. Complimentary WiFi is available throughout the property.',
        'https://testimages.com/5', 5),
       ('Comfy flat in the heart of St Leonards', 'Moscow, Lavrov St. 18, 1315', 56.1, 39.8, 675, 5, 2,
        'Situated in Northolt, 1.6 km from Northolt and 1.9 km from Greenford, No2326 Tree-Hole provides accommodation with amenities such as free WiFi and a flat-screen TV.',
        'https://testimages.com/5', 5),
       ('The James Manchester', 'Moscow, Peonistov St. 11, 11', 56.0, 39.0, 852, 5, 1,
        'Within 16 km of Aysgarth Falls and 27 km of Forbidden Corner, The Wharfe - Cosy, comfortable retreat features free WiFi and a garden.',
        'https://testimages.com/5', 6),
       ('Haworth Heights - An AMAZING Aparthotel!', 'Moscow, Kelov St. 1, 725', 54.1, 40.9, 322, 3, 4,
        'Located in Manchester, near Opera House Manchester, John Rylands Library and Albert Square, Crown X Chapel Apartments features free WiFi, and guests can enjoy a garden and a terrace.',
        'https://testimages.com/5', 2),
       ('The Duplex Margate with Deck, Mini Bar & Air Conditioning', 'Moscow, Kiroaq St. 2, 225', 54.2, 39.1, 228, 3, 3,
        'Located in West Bromwich, 6.6 km from Museum of the Jewellery Quarter and 8.2 km from Birmingham Museum & Art Gallery, Duplex Apartments By Luxe Your Stays- Sandwell, West Bromwich provides city views...',
        'https://testimages.com/5', 3),
       ('Belgrade Plaza Serviced Apartments', 'Moscow, Limanova St. 13, 53', 57.0, 40.8, 862, 5, 5,
        'Located in Instow, 8.2 km from Royal North Devon Golf Club and 8.8 km from Westward Ho!, Lovely flat set in parish boundary of Instow provides accommodation with amenities such as free WiFi and a...',
        'https://testimages.com/5', 1),
       ('Dunes View - Atlantic Bay', 'Moscow, Vinova St. 8, 325', 57.9, 42.9, 563, 5, 4,
        'Situated in Parkside, 300 metres from FarGo Village and 6.9 km from Ricoh Arena, Bramble Studios - Luxury 1-Bed Serviced Suites features accommodation with free WiFi, air conditioning, a garden and a...',
        'https://testimages.com/5', 3);



INSERT INTO Reservations(start_date, end_date, room_id, user_id)
VALUES ('2023-02-23', '2023-02-23', 5, 7),
       ('2023-02-13', '2023-02-16', 5, 7),
       ('2023-02-13', '2023-02-15', 4, 7),
       ('2023-02-22', '2023-02-26', 3, 7),
       ('2023-02-13', '2023-02-15', 2, 6),
       ('2023-02-22', '2023-02-26', 1, 6),
       ('2023-02-13', '2023-02-15', 6, 6),
       ('2023-02-22', '2023-02-26', 7, 6),
       ('2023-02-22', '2023-02-26', 8, 6),
       ('2023-02-22', '2023-02-26', 9, 6),
       ('2023-02-22', '2023-02-26', 10, 6),
       ('2023-02-22', '2023-02-26', 11, 6),
       ('2023-02-22', '2023-02-26', 12, 6),
       ('2023-02-22', '2023-02-26', 13, 6);


DROP TABLE IF EXISTS user_roles;
drop table if exists role;
drop table if exists reservations;
drop table if exists rooms;
drop table if exists users;
DROP sequence if exists rolesSerial;
DROP sequence if exists reservationsSerial;
DROP sequence if exists roomsSerial;
DROP sequence if exists usersSerial;
