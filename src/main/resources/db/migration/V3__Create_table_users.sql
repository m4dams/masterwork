Create table if not exists `users` (
    `id` integer unsigned not null auto_increment primary key,
    `user_name` varchar(25)  not null unique ,
    `password` varchar (255)  not null ,
    `email`    varchar (255)   not null unique ,
    `first_name` varchar (50) not null,
    `last_name` varchar (50)  not null,
    `date_of_birth` date not null,
    `address_id` integer unsigned not null,
    `authorization` integer unsigned not null,
    foreign key (`address_id`) references addresses (id),
    foreign key (`authorization`) references access_levels(id)
);