package models.persons

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

case class Address(id: Long, street: String, ms_ref: Long)

object Address {
	val addressParser: RowParser[Address] = {
		long("id") ~ str("street") ~ long("ms_ref") map {
			case id ~ street ~ ms_ref => Address(id, street, ms_ref)
		}
	}

	def insert(a: Address): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""insert into
						address
					(street,ms_ref) values
					({street},{ms_ref})
				""").on(
					"street" -> a.street,
					"ms_ref" -> a.ms_ref
				).executeUpdate() == 1
		}
	}

}
