-- в учебных целях заполним базу тестовыми данными
--В реальном приложении будем подключение к реальной БД или подтягивание начальных данных из NSI/ MDM

insert into author (name, author_id)
values ('Pushkin', 1),('Tolstoy', 2);

insert into genre (name, genre_id)
values ('poetry', 1), ('prose', 2);

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