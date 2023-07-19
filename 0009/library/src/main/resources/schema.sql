CREATE TABLE IF NOT EXISTS author
(
    author_id   BIGSERIAL PRIMARY KEY,
    NAME text not null

);

CREATE TABLE IF NOT EXISTS genre
(
    genre_id BIGSERIAL PRIMARY KEY,
    NAME text not null

);

CREATE TABLE IF NOT EXISTS BOOK
(
    NAME text not null PRIMARY KEY,
    author_id BIGSERIAL not null,
    genre_id BIGSERIAL not null,

    CONSTRAINT author_dfk FOREIGN KEY (author_id) REFERENCES author(author_id) ON DELETE CASCADE,
    CONSTRAINT genre_dfk FOREIGN KEY (genre_id) REFERENCES genre(genre_id) ON DELETE CASCADE
);



