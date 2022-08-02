create table if not exists `authors_books` (
    `id` integer unsigned not null auto_increment primary key,
    `authors_id` integer unsigned not null,
    `books_id` integer unsigned not null,
    foreign key (`authors_id`) references authors(id),
    foreign key (`books_id`) references books(id)
);