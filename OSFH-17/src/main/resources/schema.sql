CREATE TABLE IF NOT EXISTS AUTHORS(ID SERIAL PRIMARY KEY, FIRST_NAME VARCHAR(255), MIDDLE_NAME VARCHAR(255), LAST_NAME VARCHAR(255));

CREATE TABLE IF NOT EXISTS GENRES(ID SERIAL PRIMARY KEY, NAME VARCHAR(255) UNIQUE);

CREATE TABLE IF NOT EXISTS BOOKS(ID SERIAL PRIMARY KEY,
    NAME VARCHAR(255),
    AUTHOR_ID BIGINT NOT NULL,
    FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS(ID),
    GENRE_ID BIGINT NOT NULL,
    FOREIGN KEY (GENRE_ID) REFERENCES GENRES(ID)
);

CREATE TABLE IF NOT EXISTS COMMENTS(ID SERIAL  PRIMARY KEY,
    COMMENT VARCHAR(255),
    BOOK_ID BIGINT,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(ID)
);

