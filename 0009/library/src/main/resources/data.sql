insert into author (name)
values ('Pushkin'),('Tolstoy');

insert into genre (name)
values ('poetry'), ('prose');

insert into book (name, author_id, genre_id)
values ('Eugene Onegin', 1, 1), ('War and Peace', 2, 2);