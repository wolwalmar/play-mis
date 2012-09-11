package models.persons

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

import models.db._

case class PremiumAdress(
	ms_id: Long,
	sdgs: String,
	adrMerk: String,
	e_na1: String,
	e_na2: String,
	e_na3: String,
	e_na4: String,
	e_str: String,
	e_hnr: String,
	e_plz: String,
	e_ort: String,
	e_postf: String,
	nsa_na1: String,
	nsa_na2: String,
	nsa_na3: String,
	nsa_na4: String,
	nsa_str: String,
	nsa_hnr: String,
	nsa_plz: String,
	nsa_ort: String,
	nsa_land: String,
	status: Int)

object PremiumAdress extends DbAccess {
	type Entity = PremiumAdress

	override val tablename = "premiumadress"

	override val rowParser: RowParser[PremiumAdress] = {
		long("ms_id") ~ 
		str("sdgs") ~ str("adrMerk") ~ 
		str("e_na1") ~ str("e_na2") ~ str("e_na3") ~ str("e_na4") ~ 
		str("e_str") ~ str("e_hnr") ~ str("e_plz") ~ str("e_ort") ~ str("e_postf") ~ 
		str("nsa_na1") ~ str("nsa_na2") ~ str("nsa_na3") ~ str("nsa_na4") ~ 
		str("nsa_str") ~ str("nsa_hnr") ~ str("nsa_plz") ~ str("nsa_ort") ~ str("nsa_land") ~ int("status") map {
			case ms_id ~ sdgs ~ adrMerk ~ e_na1 ~ e_na2 ~ e_na3 ~ e_na4 ~ e_str ~ e_hnr ~ e_plz ~ e_ort ~ e_postf ~ nsa_na1 ~ nsa_na2 ~ nsa_na3 ~ nsa_na4 ~ nsa_str ~ nsa_hnr ~ nsa_plz ~ nsa_ort ~ nsa_land ~ status => 
				PremiumAdress(ms_id,sdgs,adrMerk,e_na1,e_na2,e_na3,e_na4,e_str,e_hnr,e_plz,e_ort,e_postf,nsa_na1,nsa_na2,nsa_na3,nsa_na4,nsa_str,nsa_hnr,nsa_plz,nsa_ort,nsa_land,status)
		}
	}

	def clear: Boolean = DB.withConnection {
		implicit connection => 
		SQL("delete from "+tablename).executeUpdate() == 1
	}


	def insert(a: PremiumAdress): Boolean = {
		DB.withConnection {
			implicit connection => {
			val sql = SQL("""insert into
								premiumadress
							(ms_id,sdgs,adrMerk,
								e_na1,e_na2,e_na3,
								e_na4,e_str,e_hnr,
								e_plz,e_ort,e_postf,
								nsa_na1,nsa_na2,nsa_na3,
								nsa_na4,nsa_str,nsa_hnr,
								nsa_plz,nsa_ort,nsa_land,
								status
							) values (
								{ms_id},{sdgs},{adrMerk},
								{e_na1},{e_na2},{e_na3},
								{e_na4},{e_str},{e_hnr},
								{e_plz},{e_ort},{e_postf},
								{nsa_na1},{nsa_na2},{nsa_na3},
								{nsa_na4},{nsa_str},{nsa_hnr},
								{nsa_plz},{nsa_ort},{nsa_land},
								{status}
							)""").on(
								"ms_id" -> a.ms_id,
								"sdgs" -> a.sdgs,
								"adrMerk" -> a.adrMerk,
								"e_na1" -> a.e_na1,
								"e_na2" -> a.e_na2,
								"e_na3" -> a.e_na3,
								"e_na4" -> a.e_na4,
								"e_str" -> a.e_str,
								"e_hnr" -> a.e_hnr,
								"e_plz" -> a.e_plz,
								"e_ort" -> a.e_ort,
								"e_postf" -> a.e_postf,
								"nsa_na1" -> a.nsa_na1,
								"nsa_na2" -> a.nsa_na2,
								"nsa_na3" -> a.nsa_na3,
								"nsa_na4" -> a.nsa_na4,
								"nsa_str" -> a.nsa_str,
								"nsa_hnr" -> a.nsa_hnr,
								"nsa_plz" -> a.nsa_plz,
								"nsa_ort" -> a.nsa_ort,
								"nsa_land" -> a.nsa_land,
								"status" -> a.status)
				sql.executeUpdate() == 1
			}
		}
	}
}
