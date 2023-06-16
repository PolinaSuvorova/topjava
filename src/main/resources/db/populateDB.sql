DELETE
FROM user_role;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, calories_per_day)
VALUES ('User', 'user@yandex.ru', 'password', 2000),
       ('Admin', 'admin@gmail.com', 'admin', 1000),
       ('Guest', 'guest@gmail.com', 'guest', 100);

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2023-06-16 07:10:00.000000', 'Завтрак', 1000),
       (100000, '2023-06-16 13:10:00.000000', 'Обед', 2000);