create table if not exists `categories_books` (
    `id` integer unsigned not null auto_increment primary key,
    `categories_id` integer unsigned not null,
    `books_id` integer unsigned not null,
    foreign key (`categories_id`) references categories(id),
    foreign key (`books_id`) references books(id)
);