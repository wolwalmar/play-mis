package models.memberships

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

case class Membership(
	id: Long,
	membershipId: Long,
	begin: Date
)

object Membership {
	val membershipParser: RowParser[Membership] = {
		long("id") ~ 
		long("ms_id") ~
		date("begin") map {
			case id ~ membershipId ~ begin => Membership(id,membershipId,begin)
		}
	}

	val membershipsParser: ResultSetParser[List[Membership]] = {
		membershipParser *
	}

	def findAll: List[Membership] = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from membership")
		sql.as(membershipsParser)
	}

	def findByMembershipId(membershipId: Long): Membership  = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from membership where ms_id={membershipId}").on("membershipId" -> membershipId)
		sql.as(membershipsParser).head
	}

	def findById(id: Long): Membership  = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from membership where id={id}").on("id" -> id)
		sql.as(membershipsParser).head
	}

	def insert(membership:Membership): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""insert into
						membership
					(ms_id,begin) values
					({ms_id},{begin})
				""").on(
					"ms_id" -> membership.membershipId,
					"begin" -> membership.begin
				).executeUpdate() == 1
		}
	}
}