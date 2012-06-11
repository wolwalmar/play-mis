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

	def findByMsRef(ms_ref: Long): Person = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from person where ms_ref={ms_ref}").on("ms_ref" -> ms_ref)
		sql.as(personParser *).head
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