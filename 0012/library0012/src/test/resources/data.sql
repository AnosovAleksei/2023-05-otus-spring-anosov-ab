--набор тестовых данных

insert into author (name)--, author_id)
values ('author1'),('author2');


insert into genre (name)--, genre_id)
values ('genre1'), ('genre2');

insert into book (name, author_id, genre_id)
values ('book1',
        select author_id from author where name = 'author1',
        select genre_id from genre where name = 'genre1'),
        ('book2',
        select author_id from author where name = 'author2',
        select genre_id from genre where name = 'genre2');