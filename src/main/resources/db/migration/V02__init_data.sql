insert into animals
values(nextval('animals_animal_id_seq'), 'baloo', 'dog', 'yorkie', 3, 'good');
insert into animals
values(nextval('animals_animal_id_seq'), 'simba', 'lion', 'king', 10, 'ok');
insert into animals
values(nextval('animals_animal_id_seq'), 'pegasus', 'horse', 'flying horse', 5, 'excellent');

insert into users
values(nextval('users_user_id_seq'),'admin@admin.com', '$2a$12$ZRVouxF3f.uu0a0Vz9IIwe8twP/E/cGQGh/iEiRdHHQZx3lQ3uX/W', now(), true);

insert into users
values(nextval('users_user_id_seq'),'user@user.com', '$2a$12$ZsJGCkrEcBezSGB.IIzQ/uUatsc.4/CewBnfSwHECQgN/1AvIf8q2', now(), true);

insert into authorities
values (nextval('authorities_authority_id_seq'),'ADMIN');

insert into authorities
values (nextval('authorities_authority_id_seq'),'USER');

insert into users_authorities
values(1, 1);

insert into users_authorities
values(1, 2);

insert into users_authorities
values(2, 2);