-- auto-generated definition
create table store
(
    id      bigint auto_increment
        primary key,
    name    varchar(20)  null,
    address varchar(100) null
);

-- auto-generated definition
create table staff
(
    id       bigint auto_increment
        primary key,
    store_id bigint      null,
    name     varchar(10) null,
    age      int         null
);

