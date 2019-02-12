/*
drop table if exists user;
drop table if exists auth_token;
drop table if exists people;
drop table if exists event;
*/

create table if not exists user (
    username   varchar(255)  not null primary key,
    password   varchar(255)  not null,
    email      varchar(255)  not null,
    firstname  varchar(255)  not null,
    lastname   varchar(255)  not null,
    gender     varchar(255)  not null, /* f or m */
    person_id  varchar(255)  not null
);

create table if not exists auth_token (
    token      varchar(255)  not null primary key,
    username   varchar(255)  not null
);

create table if not exists people (
    person_id  varchar(255)  not null primary key,
    descendant varchar(255),           /* username */
    firstname  varchar(255)  not null,
    lastname   varchar(255)  not null,
    gender     varchar(255)  not null,
    father_id  varchar(255),
    mother_id  varchar(255),
    spouse_id  varchar(255)
);

create table if not exists event (
    event_id   varchar(255)  not null primary key,
    descendant varchar(255),           /* username */
    person_id  varchar(255)  not null,
    latitude   varchar(255)  not null, /* ###.###### */
    longitude  varchar(255)  not null, /* ###.###### */
    country    varchar(255)  not null,
    city       varchar(255)  not null,
    event_type varchar(255)  not null,
    year       integer       not null  /* 4 digits max. negative nums represent b.c. */
);
