insert into users (name, email, password)
values ('Pashka Mishkin', 'EmailWithStrongPassword@mail.ru', '$2a$10$X/7Olfqi2AFqeYnPvnmO9uUXPyI0bMPkvIKoGBbRcX5pF4RJxCrau'), -- PashkaM8
       ('Dima Bilan', 'DimaBilan@yandex.ru', '$2a$10$mec7gSVnJrVaSR6BpE9k1.MjdgG3ZJIw0yIqLxdh09PfVJ65cTA7a'), -- Bilan1
       ('Bruno Mars', 'Bruno@Mars.com', '$2a$10$W1CEPiWhbq..WzomGOba.OuQ.AA7BoT0m3HVg7MQ0W1XneUVP/IDe'); -- BruBest1

insert into tasks (title, description, status, expiration_date)
values ('Buy cheese', 'desc', 'TODO', '2024-01-29 12:00:00'),
       ('Do homework', 'Math, Physics, Literature', 'IN_PROGRESS', '2024-01-31 00:00:00'),
       ('Clean rooms', null, 'DONE', null),
       ('Call Dimke Bilan', 'Ask about meeting', 'TODO', '2024-02-01 00:00:00');

insert into users_tasks (task_id, user_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);