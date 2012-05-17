package models.persons

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

case class Person(id: Long, name: String)

object Person {
	val personParser: RowParser[Person] = {
		long("id") ~ str("name") map {
			case id ~ name => Person(id, name)
		}
	}

}