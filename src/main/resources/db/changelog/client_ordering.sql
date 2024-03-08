--liquibase formatted sql
--changeset Niko Potylitsin:1
create table client_ordering (
    client_id uuid not null constraint fk_client_ordering_client references client (id),
    ordering_id uuid not null constraint fk_client_ordering_ordering references ordering (id),
    constraint uq_client_ordering_client_id_ordering_id unique (client_id, ordering_id)
);
--changeset Niko Potylitsin:2
insert into client_ordering (client_id, ordering_id)
values
    ('687f3d3b-d83b-4b68-be49-37a13ac0a5de', 'b9ae34d1-7803-4600-b9bc-f66b165a40e1'),
    ('983acc7e-8aab-49db-889f-ca4a442d9e16', '74e66cfe-62a3-48c2-aa57-1d11b175e72a'),
    ('a0c99a02-c7c4-4d97-afc7-e8739603c693', 'e17e03a2-5741-4b49-b6b6-91868a4fcbc5'),
    ('2e845814-ce50-43ce-91a0-12bbaefdc95b', '3bc2cc19-f447-4e84-8ae0-b983e4020bab')