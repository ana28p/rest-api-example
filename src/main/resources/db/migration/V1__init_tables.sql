drop table if exists quote;
drop table if exists actor;
drop table if exists movie;

create table movie (
    id serial primary key,
    name varchar(255) not null
);

create table actor (
   id serial primary key,
   name varchar(255) not null
);

create table quote (
    id serial primary key,
    text varchar(255) not null,
    movie_id int,
    actor_id int,
    constraint fk_quote_movie foreign key(movie_id) references movie(id),
    constraint fk_quote_actor foreign key(actor_id) references actor(id)
);
