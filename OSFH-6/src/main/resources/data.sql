insert into authors (first_name, middle_name, last_name) values ('Ivan', 'Ivanovich', 'Ivanov');
insert into authors (first_name) values ('Petr');

insert into genres (name) values ('Tutorial');
insert into genres (name) values ('Story');

insert into books (name, author_id, genre_id) values ('ABC', 1, 1);
insert into books (name, author_id, genre_id) values ('123', 1, 1);

insert into comments (comment, book_id) values ('blablabla', 1);
insert into comments (comment, book_id) values ('tralala', 1);
