insert into author (id, name)
values (1, 'pushkin'),(2, 'lermontov');

insert into genre (id, name)
values (1, 'history'), (2, 'dramma');

insert into book (name, author_id, genre_id)
values ('historyBook', 1, 1), ('drammaBook', 2, 2);