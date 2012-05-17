# --- !Ups

insert into membership(ms_id,begin) values (1,'2012-01-01');
insert into person(name,ms_id) values ('hans',1);
# --- !Downs

delete from person;
delete from membership;