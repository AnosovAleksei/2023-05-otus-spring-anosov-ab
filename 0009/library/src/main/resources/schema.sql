DROP TABLE IF EXISTS author;
CREATE TABLE author
(
    id   BIGSERIAL PRIMARY KEY,
    NAME text not null

);

DROP TABLE IF EXISTS genre;
CREATE TABLE genre
(
    id   BIGSERIAL PRIMARY KEY,
    NAME text not null

);

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK
(
    NAME text not null PRIMARY KEY,
    author_id BIGSERIAL not null,
    genre_id BIGSERIAL not null,

    CONSTRAINT author_dfk FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE,
    CONSTRAINT genre_dfk FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);



