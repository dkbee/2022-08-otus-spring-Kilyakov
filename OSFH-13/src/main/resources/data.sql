insert into authors (first_name, middle_name, last_name) values ('Ivan', 'Ivanovich', 'Ivanov');
insert into authors (first_name) values ('Petr');

insert into genres (name) values ('Tutorial');
insert into genres (name) values ('Story');

insert into books (name, author_id, genre_id) values ('ABC', 2, 1);
insert into books (name, author_id, genre_id) values ('123', 1, 2);

insert into comments (comment, book_id) values ('blablabla', 1);
insert into comments (comment, book_id) values ('tralala', 1);
insert into comments (comment, book_id) values ('nice', 2);
insert into comments (comment, book_id) values ('terrible', 2);

insert into users (username, password, enabled) values ('admin', 'password', true);
insert into users (username, password, enabled) values ('user', 'password', true);
insert into users (username, password, enabled) values ('guest', 'password', true);

insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', 'ROLE_GUEST');

insert into authorities (username, authority) values ('user', 'ROLE_USER');
insert into authorities (username, authority) values ('user', 'ROLE_GUEST');

insert into authorities (username, authority) values ('guest', 'ROLE_GUEST');