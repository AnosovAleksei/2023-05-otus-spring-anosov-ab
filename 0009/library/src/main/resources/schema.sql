DROP TABLE IF EXISTS author;
CREATE TABLE author
(
    id   int8 PRIMARY KEY,
    NAME text not null

);

DROP TABLE IF EXISTS genre;
CREATE TABLE genre
(
    id   int8 PRIMARY KEY,
    NAME text not null

);

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME text not null,
    author_id int8 not null,
    genre_id int8 not null,

    CONSTRAINT author_dfk FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE,
    CONSTRAINT genre_dfk FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);

