# --- !Ups

create table membership (
	id long Identity primary key,
	ms_id long not null,
	begin date not null
);

# --- !Downs
drop table membership;
