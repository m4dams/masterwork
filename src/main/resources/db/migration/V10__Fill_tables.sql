insert into access_levels (id, name, description) values
    (1, 'USER', 'Can search for books.'),
    (2, 'LIBRARIAN', 'Can add, search, modify and delete books. Can search users.'),
    (3, 'ADMIN', 'Full access. Can add, search, modify and delete books and users.');

insert into categories (id, name ) values
(1, 'this'),
(2, 'that'),
(3, 'else'),
(4, 'whatever');

insert into authors (id, name ) values
(1, 'Bob'),
(2, 'anonymous'),
(3, 'unknown');

insert into availabilities (id, name) values
(1,'AVAILABLE'),
(2,'INPLACEUSE'),
(3,'COPYONLY'),
(4,'RESTORATION');

