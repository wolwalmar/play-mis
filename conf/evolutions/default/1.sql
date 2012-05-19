# --- !Ups
create Sequence ms_id_seq;

create table membership (
	id long primary key,
	ms_id long not null unique ,
	begin date not null
);

create table person (
	id long Identity primary key,
	name varchar(50),
	ms_ref long,
	foreign key (ms_ref) references membership(id) 
);

create table account (
	id long Identity primary key,
	postingtext varchar(100),
	posted date,
	amount decimal(10,2),
	ms_ref long,
	foreign key (ms_ref) references membership(id) 
);

# --- !Downs
drop table person;
drop table membership;
drop sequence ms_id_seq;

