--liquibase formatted sql
--changeset Niko Potylitsin:3
create table users
(
    id       uuid not null primary key,
    username varchar(1024),
    password varchar(1024),
    user_type varchar(1024)
);