package models.persons

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

case class Person(id: Long, firstname: String, lastname: String, ms_ref: Long)

object Person {
	val personParser: RowParser[Person] = {
		long("id") ~ str("firstname") ~ str("lastname") ~ long("ms_ref") map {
			case id ~ firstname ~ lastname ~ ms_ref => Person(id, firstname, lastname, ms_ref)
		}
	}

	def insert(p: Person): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""insert into
						person
					(firstname,lastname,ms_ref) values
					({firstname},{lastname},{ms_ref})
				""").on(
					"firstname" -> p.firstname,
					"lastname" -> p.lastname,
					"ms_ref" -> p.ms_ref
				).executeUpdate() == 1
		}
	}


}