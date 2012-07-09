package models.persons

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

case class LegalProtectionInsurance(
	id: Long,
	begin_rsv: Date,
	end_rsv: Option[Date],
	contrib: Int)

object LegalProtectionInsurance {
	val lpiParser: RowParser[LegalProtectionInsurance] = {
		long("id") ~ date("begin_rsv") ~ get[Option[Date]]("end_rsv") ~ int("contrib") map {
			case id ~ begin_rsv ~ end_rsv ~ contrib => LegalProtectionInsurance(id, begin_rsv, end_rsv, contrib)
		}
	}

	val lpiRSParser: ResultSetParser[List[LegalProtectionInsurance]] = {
		lpiParser *
	}

	val lpiIdParser: RowParser[Long] = {
		long("next") map {
			case next => next
		}
	}

	val lpiIdRSParser: ResultSetParser[List[Long]] = {
		lpiIdParser *
	}

	def findById(id: Long): LegalProtectionInsurance = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from rsv where id={id}").on("id" -> id)
		sql.as(lpiParser *).head
	}

	def insert(a: LegalProtectionInsurance): Long = {
		DB.withConnection {
			implicit connection =>
			val next = nextSeqNum
			SQL("""insert into
						rsv
					(id,begin_rsv, end_rsv, contrib) values
					({id},{begin_rsv},{end_rsv},{contrib})
				""").on(
					"id" -> next,
					"begin_rsv" -> a.begin_rsv,
					"end_rsv" -> a.end_rsv,
					"contrib" -> a.contrib
				).executeUpdate()
			next
		}
	}

	def update(a: LegalProtectionInsurance): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""update
						rsv
					set 
						"begin_rsv" -> a.begin_rsv,
						"end_rsv" -> a.end_rsv,
						"contrib" -> a.contrib 
					where
						id={id}""")
			.on(		
				"begin_rsv" -> a.begin_rsv,
				"end_rsv" -> a.end_rsv,
				"contrib" -> a.contrib,
				"id" -> a.id).executeUpdate == 1
		}
	}

	def delete(a: LegalProtectionInsurance): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""delete from
						rsv
					where
						id={id}""")
			.on("id" -> a.id).executeUpdate == 1
		}
	}

	def findLegalProtectionById(id: Long): LegalProtectionInsurance = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from rsv where id={id}").on("id" -> id)
		sql.as(lpiRSParser).head
	}

	def nextSeqNum: Long = DB.withConnection {
		implicit connection =>
			return SQL("select rsv_id_seq.nextval as next from dual").as(lpiIdRSParser).head
	}

}