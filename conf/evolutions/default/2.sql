# --- !Ups

insert into membership(id,ms_id,begin) values (1,1,'2012-01-01');
insert into person(name,ms_ref) values ('hans',1);
insert into account(postingtext,posted,amount,ms_ref) values ('Beitrag','2012-01-01',-63.9,1);
insert into account(postingtext,posted,amount,ms_ref) values ('Zlg. Beitrag','2012-02-04',63.9,1);
insert into account(postingtext,posted,amount,ms_ref) values ('Schreibgebühr','2012-04-21',-5.5,1);
insert into address(street,ms_ref) values ('Talweg',1);
# --- !Downs

delete from account;
delete from address;
delete from person;
delete from membership;