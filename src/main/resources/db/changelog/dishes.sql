--liquibase formatted sql
--changeset Niko Potylitsin:1
create table dishes
(
    id              serial  not null primary key,
    name            varchar(1024),
    price           numeric(15, 2),
    minutes_to_cook integer not null
);