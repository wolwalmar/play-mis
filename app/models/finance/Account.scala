package models.finance

import java.util.Date

import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import java.math.BigDecimal

import play.api.db.DB
import play.api.Play.current
case class Account(id: Long, postingtext: String, posted: Date, amount: BigDecimal, ms_ref: Long)

object Account {
	val accountParser: RowParser[Account] = {
		long("id") ~ str("postingtext") ~ date("posted") ~ long("ms_ref") ~ get[BigDecimal]("amount") map {
			case id ~ postingtext ~ posted ~ ms_ref ~ amount => Account(id, postingtext, posted, amount, ms_ref)
		}
	}

	def findAllByMsId(membershipId: Long): List[Account] = DB.withConnection {
		implicit connection =>
		val sql = SQL("select * from account where ms_ref={membershipId}").on("membershipId" -> membershipId)
		sql.as(accountParser *)
	}

	def insert(a: Account): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""insert into
						account 
					(postingtext,posted, amount, ms_ref) values
					({postingtext},{posted},{amount},{ms_ref})
				""").on(
					"postingtext" -> a.postingtext,
					"posted" -> a.posted,
					"amount" -> a.amount,
					"ms_ref" -> a.ms_ref
				).executeUpdate() == 1
		}
	}

}
