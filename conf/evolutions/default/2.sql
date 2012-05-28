# --- !Ups

insert into membership(id,ms_id,begin_ms,end_ms,contrib) values (1,1,'2012-01-01','2014-12-31',1);
insert into person(salutation,title,firstname,lastname,birthday,ms_ref) values ('Herr', 'Dr.', 'Hans','Huckebein','1978-06-09',1);
insert into account(postingtext,posted,amount,ms_ref) values ('Beitrag','2012-01-01',-63.9,1);
insert into account(postingtext,posted,amount,ms_ref) values ('Zlg. Beitrag','2012-02-04',63.9,1);
insert into account(postingtext,posted,amount,ms_ref) values ('Schreibgebühr','2012-04-21',-5.5,1);
insert into address(street,number,zip,city,ms_ref) values ('Talweg','16','53229','Bonn',1);
# --- !Downs

delete from account;
delete from address;
delete from person;
delete from membership;