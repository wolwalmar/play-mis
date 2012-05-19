package models.persons

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

case class Person(id: Long, name: String, ms_ref: Long)

object Person {
	val personParser: RowParser[Person] = {
		long("id") ~ str("name") ~ long("ms_ref") map {
			case id ~ name ~ ms_ref => Person(id, name, ms_ref)
		}
	}

	def insert(p: Person): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""insert into
						person
					(name,ms_ref) values
					({name},{ms_ref})
				""").on(
					"name" -> p.name,
					"ms_ref" -> p.ms_ref
				).executeUpdate() == 1
		}
	}


}