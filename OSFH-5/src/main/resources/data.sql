insert into authors (id, first_name, middle_name, last_name) values (1, 'Иван', 'Иванович', 'Иванов');
insert into genres (id, name) values (1, 'Tutorial');

--insert into authors (id, first_name, middle_name, last_name) values (2, '123', 'Иванович', 'Иванов');
--insert into genres (id, name) values (2, '123');


insert into books (id, name, author_id, genre_id) values (1, 'ABC', 1, 1);

insert into books (id, name, author_id, genre_id) values (2, '123', 1, 1);
