package models.db

import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

trait DbWriteAccess {
	type Entity = { def toListOfParameterTuples: List[(Any,ParameterValue[_])] }
	def tablename : String

	def insert(p: Entity): Boolean = {

		DB.withConnection {
			implicit connection =>
			SQL("""insert into
						person
					(salutation,title,firstname,lastname,birthday,ms_ref) values
					({salutation},{title},{firstname},{lastname},{birthday},{ms_ref})
				""").on(
					p.toListOfParameterTuples : _*
				).executeUpdate() == 1
		}
	}
}
