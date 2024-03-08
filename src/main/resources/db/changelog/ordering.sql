--liquibase formatted sql
--changeset Niko Potylitsin:1
create table ordering (
    id uuid not null constraint pk_ordering primary key,
    name varchar(1024) not null
);

--changeset Niko Potylitsin:2
insert into ordering (id, name)
values
    ('b9ae34d1-7803-4600-b9bc-f66b165a40e1', 'Myaso'),
    ('74e66cfe-62a3-48c2-aa57-1d11b175e72a', 'Bobi'),
    ('e17e03a2-5741-4b49-b6b6-91868a4fcbc5', 'Eat'),
    ('3bc2cc19-f447-4e84-8ae0-b983e4020bab', 'Kasha')
