# --- !Ups

insert into membership(ms_id,begin) values (1,'2012-01-01')
# --- !Downs

delete from membership;