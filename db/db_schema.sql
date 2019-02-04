/*
drop table if exists user;
drop table if exists auth_token;
drop table if exists people;
drop table if exists event;
*/

create table if not exists user (
    username   varchar(255)  not null,
    password   varchar(255)  not null,
    email      varchar(255)  not null,
    firstname  varchar(255)  not null,
    lastname   varchar(255)  not null,
    gender     char(1)       not null, /* f or m */
    person_id  integer       not null
);

create table if not exists auth_token (
    token      integer       not null,
    username   varchar(255)  not null
);

create table if not exists people (
    person_id  integer       not null,
    descendant varchar(255),           /* username */
    firstname  varchar(255)  not null,
    lastname   varchar(255)  not null,
    gender     char(1)       not null,
    father_id  integer,
    mother_id  integer,
    spouse_id  integer
);

create table if not exists event (
    event_id   integer       not null,
    descendant varchar(255),           /* username */
    person_id  integer       not null,
    latitude   Decimal(9,6)  not null, /* ###.###### */
    longitude  Decimal(9,6)  not null, /* ###.###### */
    country    varchar(255)  not null,
    city       varchar(255)  not null,
    event_type varchar(255)  not null,
    year       integer(4)    not null  /* 4 digits max. negative nums represent b.c. */
);
