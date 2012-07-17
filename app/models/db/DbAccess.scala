package models.db

import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

trait DbAccess {
	type Entity
	def tablename: String
	def rowParser: RowParser[Entity]

	lazy val resultSetParser = rowParser *

	def findAll: List[Entity] = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from " + tablename)
		sql.as(resultSetParser)
	}	

	def findById(id: Long): Entity  = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from "+tablename+" where id={id}").on("id" -> id)
		sql.as(resultSetParser).head
	}

	def findByMsRef(ms_ref: Long): Entity = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from "+tablename+" where ms_ref={ms_ref}").on("ms_ref" -> ms_ref)
		sql.as(rowParser *).head
	}


	val scalarParser: RowParser[Long] = {
		long("next") map {
			case next => next
		}
	}

	val scalarResultSetParser: ResultSetParser[List[Long]] = {
		scalarParser *
	}

	def nextSeqNum(idSeqName: String): Long = DB.withConnection {
		implicit connection =>
			SQL("select "+idSeqName+".nextval as next from dual").as(scalarResultSetParser).head
	}

}