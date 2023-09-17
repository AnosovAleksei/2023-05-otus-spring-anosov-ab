-- в учебных целях заполним базу тестовыми данными
--В реальном приложении будем подключение к реальной БД или подтягивание начальных данных из NSI/ MDM

insert into author (name)
values ('Pushkin'),('Tolstoy');

insert into genre (name)
values ('poetry'), ('prose');

insert into book (name, author_id, genre_id)
values ('Eugene Onegin',
        select author_id from author where name = 'Pushkin',
        select genre_id from genre where name = 'poetry'),
        ('War and Peace',
        select author_id from author where name = 'Tolstoy',
        select genre_id from genre where name = 'prose');


insert into commentary (book_id, message)
values  (select book_id from book where name = 'Eugene Onegin', 'coment 1'),
        (select book_id from book where name = 'Eugene Onegin', 'coment 2');


insert into "user" (username, password, role)
values  ('user', '$2a$10$B5ykjK6.84im7N8lRovmjOf994HjtKJ/Ax46F.xGbawYDPwsfW2By', 'USER'),-- 1234
        ('admin', '$2a$10$B5ykjK6.84im7N8lRovmjOf994HjtKJ/Ax46F.xGbawYDPwsfW2By', 'ADMIN'); -- 1234