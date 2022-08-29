create table animals(
    animal_id serial primary key,
    animal_name varchar(100),
    animal_type varchar(50),
    animal_breed varchar(50),
    animal_age integer,
    animal_health varchar(10)
);
create table users(
    user_id bigserial primary key,
    user_email varchar(50),
    user_password varchar(100),
    user_created timestamp,
    user_enabled bool
);

create table authorities(
    authority_id bigserial primary key,
    authority_name varchar (50)
);

create table users_authorities(
    user_id bigint,
    authority_id bigint
);