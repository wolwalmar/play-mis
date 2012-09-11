# --- !Ups
create table premiumadress (
	ms_id long,
	sdgs char(2),
	adrMerk char(2),
	e_na1	varchar(50),
	e_na2	varchar(50),
	e_na3	varchar(50),
	e_na4	varchar(50),
	e_str	varchar(50),
	e_hnr	varchar(20),
	e_plz	char(5),
	e_ort	varchar(50),
	e_postf varchar(12),
	nsa_na1	varchar(50),
	nsa_na2 varchar(50),
	nsa_na3	varchar(50),
	nsa_na4	varchar(50),
	nsa_str	varchar(50),
	nsa_hnr	varchar(20),
	nsa_plz	varchar(12),
	nsa_ort	varchar(50),
	nsa_land varchar(50),
	status int
)

# --- !Downs
drop table premiumadress

