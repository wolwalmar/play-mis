package models.persons

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

case class Person(
	id: Long, 
	salutation: String,
	title: String,
	firstname: String, 
	lastname: String, 
	birthday: Date,
	ms_ref: Long)

object Person {
	val personParser: RowParser[Person] = {
		long("id") ~ str("salutation") ~ str("title") ~ str("firstname") ~ str("lastname") ~ date("birthday") ~ long("ms_ref") map {
			case id ~ salutation ~ title ~ firstname ~ lastname ~ birthday ~ ms_ref => Person(id, salutation, title, firstname, lastname, birthday, ms_ref)
		}
	}

	def insert(p: Person): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""insert into
						person
					(salutation,title,firstname,lastname,birthday,ms_ref) values
					({salutation},{title},{firstname},{lastname},{birthday},{ms_ref})
				""").on(
					"salutation" -> p.salutation,
					"title" -> p.title,
					"firstname" -> p.firstname,
					"lastname" -> p.lastname,
					"birthday" -> p.birthday,
					"ms_ref" -> p.ms_ref
				).executeUpdate() == 1
		}
	}


}