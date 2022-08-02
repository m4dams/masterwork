Create table if not exists `access_levels` (
    `id` integer unsigned not null primary key default 1,
    `name` varchar(50)  not null unique,
    `description` varchar(255)  not null
);