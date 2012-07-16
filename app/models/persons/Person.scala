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

case class Person(
	id: Long, 
	salutation: String,
	title: String,
	firstname: String, 
	lastname: String, 
	birthday: Option[Date],
	ms_ref: Long)

object Person extends DbAccess {
	type Entity = Person
	override val tablename = "person"

	override val rowParser: RowParser[Person] = {
		long("id") ~ str("salutation") ~ str("title") ~ str("firstname") ~ str("lastname") ~ get[Option[Date]]("birthday") ~ long("ms_ref") map {
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

	def update(p: Person): Boolean = 
		DB.withConnection {
			implicit connection =>
			SQL("""update
						person
					set 
						salutation={salutation},
						title={title},
						firstname={firstname},
						lastname={lastname},
						birthday={birthday} 
					where
						id={id}""")
			.on(		
				"salutation" -> p.salutation,
				"title" -> p.title,
				"firstname" -> p.firstname,
				"lastname" -> p.lastname,
				"birthday" -> p.birthday,
				"id" -> p.id).executeUpdate == 1
		}
}