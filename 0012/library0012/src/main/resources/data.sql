insert into author (name)
values ('Pushkin'),('Tolstoy');

insert into genre (name)
values ('poetry'), ('prose');

insert into book (name, author_id, genre_id)
values ('Eugene Onegin', 1, 1), ('War and Peace', 2, 2);

insert into commentary (book_id, message)
values (1, 'coment 1'), (1, 'coment 2');