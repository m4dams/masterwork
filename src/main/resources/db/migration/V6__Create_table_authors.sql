create table if not exists `authors` (
    `id` integer unsigned not null auto_increment primary key,
    `name` varchar(255) not null unique
    );