CREATE TABLE IF NOT EXISTS author
(
    author_id   BIGSERIAL PRIMARY KEY,
    NAME text not null UNIQUE

);

CREATE TABLE IF NOT EXISTS genre
(
    genre_id BIGSERIAL PRIMARY KEY,
    NAME text not null UNIQUE

);




CREATE TABLE IF NOT EXISTS BOOK
(
    book_id BIGSERIAL not null PRIMARY KEY,
    NAME text not null UNIQUE,
    author_id BIGSERIAL not null,
    genre_id BIGSERIAL not null,
    CONSTRAINT author_dfk FOREIGN KEY (author_id) REFERENCES author(author_id) ON DELETE CASCADE,
    CONSTRAINT genre_dfk FOREIGN KEY (genre_id) REFERENCES genre(genre_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS commentary
(
    commentary_id BIGSERIAL PRIMARY KEY,
    book_id BIGSERIAL,
    message text,
    CONSTRAINT commentary_dfk FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_lib
(
    id BIGSERIAL PRIMARY KEY,
    username text not null UNIQUE,
    password text not null UNIQUE
);