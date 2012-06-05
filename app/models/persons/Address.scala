package models.persons

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

case class Address(
		id: Long, 
		street: String, 
		number: String, 
		zip: String, 
		city: String, 
		ms_ref: Long,
		rsv_ref: Option[Long])

object Address {
	val addressParser: RowParser[Address] = {
		long("id") ~ str("street") ~ str("number") ~ str("zip") ~ str("city") ~ long("ms_ref") ~ get[Option[Long]]("rsv_ref") map {
			case id ~ street ~ number ~ zip ~ city ~ ms_ref ~ rsv_ref => Address(id, street, number, zip, city, ms_ref, rsv_ref)
		}
	}

	def insert(a: Address): Boolean = {
		DB.withConnection {
			implicit connection => {
			val sql = a.rsv_ref match {
				case Some(ref) => { println("ref "+ref); SQL("""insert into
											address
										(street,number,zip,city,ms_ref,rsv_ref) values
										({street},{number},{zip},{city},{ms_ref},{rsv_ref})
									""").on(
										"street" -> a.street,
										"number" -> a.number,
										"zip" -> a.zip,
										"city" -> a.city,
										"ms_ref" -> a.ms_ref,
										"rsv_ref" -> a.rsv_ref.get)}
				case None => SQL("""insert into
											address
										(street,number,zip,city,ms_ref) values
										({street},{number},{zip},{city},{ms_ref})
									""").on(
										"street" -> a.street,
										"number" -> a.number,
										"zip" -> a.zip,
										"city" -> a.city,
										"ms_ref" -> a.ms_ref)
				} 
				sql.executeUpdate() == 1
			}
		}
	}
}
