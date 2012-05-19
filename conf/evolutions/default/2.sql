# --- !Ups

insert into membership(id,ms_id,begin) values (1,1,'2012-01-01');
insert into person(name,ms_ref) values ('hans',1);
insert into account(postingtext,posted,amount,ms_ref) values ('Beitrag','2012-01-01',63.9,1)
# --- !Downs

delete from account;
delete from person;
delete from membership;