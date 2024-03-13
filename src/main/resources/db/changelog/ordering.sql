--liquibase formatted sql
--changeset Niko Potylitsin:9:217f4f50d0e1b04b8577d555d8570e29
create table ordering
(
    id         serial not null
        primary key,
    user_id    uuid,
    totalPrice numeric(10, 2),
    isActive   boolean
);

alter table ordering
    owner to postgres;
