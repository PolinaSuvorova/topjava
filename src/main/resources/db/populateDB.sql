DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meal;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, calories_per_day)
VALUES ('User', 'user@yandex.ru', 'password', 2000),
       ('Admin', 'admin@gmail.com', 'admin', 2000),
       ('Guest', 'guest@gmail.com', 'guest', 2000);

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meal (user_id, date_time, description, calories)
VALUES (100000, '2023-06-17 00:00:00', 'Ночной завтрак', 1000),
       (100000, '2023-06-15 07:00:00', 'Завтрак', 1000),
       (100000, '2023-06-16 07:10:00', 'Завтрак', 1000),
       (100000, '2023-06-16 13:10:00', 'Обед', 2000),
       (100001, '2023-06-17 00:00:00', 'Ночной завтрак', 1000);

