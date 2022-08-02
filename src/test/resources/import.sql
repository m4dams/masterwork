delete
from authors_books;
delete
from categories_books;
delete
from books;
delete
from availabilities;
delete
from authors;
delete
from categories;
delete
from users;
delete
from addresses;
delete
from access_levels;



insert into `access_levels` (id, name, description)
values (1, 'USER', 'Can search for books.'),
       (2, 'LIBRARIAN', 'Can add, search, modify and delete books. Can search users.'),
       (3, 'ADMIN', 'Full access. Can add, search, modify and delete books and users.');

insert into addresses (`id`, `county`, `city`, `postal_code`, `street_and_building`)
values (101, 'county1', 'city1', 00001, 'streetAndBuilding1'),
       (102, 'county2', 'city2', 00002, 'streetAndBuilding2'),
       (103, 'county2', 'city3', 00003, 'streetAndBuilding3');

insert into users (`id`, `user_name`, `password`, `email`, `first_name`, `last_name`,
                   `date_of_birth`, `address_id`, `authorization`)
values (101, 'admin', '$2a$10$NnOgUim0S/tXn6SG/h0PdOU8oJAuyi0BAxLjIy8Jb5yMh3gyZqLZS', 'email', 'firstName1',
        'lastName1',
        '1983-09-21', 101, 3),
       (102, 'librarian', '$2a$10$X7hkpl8ewHSvV1afuyhKGe5sBl4V5yKja2DYdT2EX9vUk7X.C7Zvm', 'email2', 'firstName2',
        'lastName2', '1983-09-21', 102, 2),
       (103, 'user1', '$2a$10$dDGEBu7ZjhnKNDADVLh7yOR61htSWyIr7iohysU6wJunD3Rtn9sGK', 'email3', 'firstName3',
        'lastName3',
        '1992-04-17', 103, 1);

insert into categories (`id`, `name`)
values (1, 'this'),
       (2, 'that'),
       (3, 'else'),
       (4, 'whatever');

insert into authors (`id`, `name`)
values (101, 'Bob'),
       (102, 'anonymous'),
       (103, 'someone');

insert into availabilities (`id`, `name`)
values (1, 'AVAILABLE'),
       (2, 'INPLACEUSE'),
       (3, 'COPYONLY'),
       (4, 'RESTORATION');

insert into books (`id`, `isbn`, `title`, `release_year`, `availability`, `return_time`, `user_id`)
values (101, '123456789a', 'title1', 1982, 1, null, null),
       (102, '123456789b', 'title2', 1968, 2, null, null),
       (103, '123456789c', 'title3', 1865, 3, null, null),
       (104, '123456789d', 'title1', 1992, 1, '2032-10-12', 103);

insert into authors_books (`id`, `authors_id`, `books_id`)
values (101, 101, 101),
       (102, 103, 101),
       (103, 102, 102),
       (104, 103, 103),
       (105, 101, 104),
       (106, 103, 104);

insert into categories_books (`id`, `categories_id`, `books_id`)
values (101, 2, 101),
       (102, 3, 101),
       (103, 1, 102),
       (104, 4, 103),
       (105, 2, 104),
       (106, 3, 104);