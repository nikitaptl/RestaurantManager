--liquibase formatted sql
--changeset Niko Potylitsin:3
create table ordering_dish_id
(
    ordering_id int references ordering(id),
    dish_id int
);