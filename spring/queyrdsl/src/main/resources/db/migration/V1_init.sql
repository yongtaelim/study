-- auto-generated definition
create table staff
(
    id       bigint auto_increment
        primary key,
    age      int          null,
    name     varchar(255) null,
    store_id bigint       null
);

-- auto-generated definition
create table store
(
    id      bigint auto_increment
        primary key,
    address varchar(255) null,
    name    varchar(255) null
);



