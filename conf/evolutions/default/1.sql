# --- !Ups
create Sequence ms_id_seq start 3;
create Sequence rsv_id_seq start 3;

create table membership (
	id long primary key,
	ms_id long not null unique ,
	begin_ms date not null,
	end_ms date,
	contrib integer
);

create table person (
	id long Identity primary key,
	salutation varchar(20),
	title varchar(20),
	firstname varchar(50),
	lastname varchar(50),
	birthday date,
	ms_ref long,
	foreign key (ms_ref) references membership(id) 
);

create table rsv (
	id long primary key,
	begin_rsv date not null,
	end_rsv date,
	contrib integer
);

create table address (
	id long Identity primary key,
	street varchar(50),
	number varchar(12),
	zip varchar(12),
	city varchar(50),
	ms_ref long,
	foreign key (ms_ref) references membership(id),
	rsv_ref long,
	foreign key (rsv_ref) references rsv(id) 	
);

create table contact (
	id long Identity primary key,
	phoneHome varchar(15),
	phoneOffice varchar(15),
	mobile varchar(15),
	email varchar(64),
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
drop table account;
drop table rsv;
drop table contact;
drop table address;
drop table person;
drop table membership;
drop sequence rsv_id_seq;
drop sequence ms_id_seq;

