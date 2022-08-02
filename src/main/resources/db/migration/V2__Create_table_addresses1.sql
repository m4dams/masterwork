Create table if not exists `addresses` (
    `id` integer unsigned not null auto_increment primary key ,
    `county` varchar(100) ,
     `city` varchar (100) not null,
    `postal_code` integer unsigned not null,
    `street_and_building` varchar(255) not null
);