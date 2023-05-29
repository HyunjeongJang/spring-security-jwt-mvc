drop table member;

CREATE TABLE member
(
    id          bigint auto_increment primary key,
    member_id   varchar(256) not null,
    name varchar(256) not null,
    password    varchar(256) not null,
    role        varchar(256) not null
);


