# --- !Ups

create table membership (
	id long Identity primary key,
	ms_id long not null,
	begin date not null
);

create table person (
	id long Identity primary key,
	name varchar(50),
	ms_id long,
	foreign key (ms_id) references membership(id) 
)
# --- !Downs
drop table person
drop table membership;

