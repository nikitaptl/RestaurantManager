--liquibase formatted sql
--changeset Niko Potylitsin:1
create table client (
    id uuid not null constraint pk_client primary key,
    name varchar(1024)
);

--changeset Niko Potylitsin:2
insert into client (id, name)
values
    ('687f3d3b-d83b-4b68-be49-37a13ac0a5de', 'Tyler'),
    ('983acc7e-8aab-49db-889f-ca4a442d9e16', 'Sanya'),
    ('a0c99a02-c7c4-4d97-afc7-e8739603c693', 'Niko'),
    ('2e845814-ce50-43ce-91a0-12bbaefdc95b', 'Chmo');