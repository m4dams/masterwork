create table if not exists `books` (
    `id` integer unsigned auto_increment not null primary key,
    `isbn` varchar(10) not null,
    `title` varchar(255)  not null,
    `release_year` integer unsigned not null,
    `availability` integer unsigned not null,
    `return_time` date,
    `user_id` integer unsigned,
    foreign key (`availability`) references availabilities(id),
    foreign key (`user_id`) references users(id)
)