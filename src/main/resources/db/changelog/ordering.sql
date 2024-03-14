--liquibase formatted sql
--changeset Niko Potylitsin:2
create table ordering
(
    id         serial not null
        primary key,
    username   varchar(1024),
    total_price numeric(10, 2),
    is_active  boolean
);
