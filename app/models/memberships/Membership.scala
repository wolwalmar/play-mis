package models.memberships

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

import models.persons._

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

	def membershipPersonParser: RowParser[(Membership,Person)] = {
		membershipParser ~ Person.personParser map (flatten)
	}

	def findAll: List[Membership] = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from membership")
		sql.as(membershipsParser)
	}

	def findAllMembershipsPersons: List[(Membership,Person)] = DB.withConnection {
		implicit connection => 
		val sql = SQL("select m.*,p.* from membership m join person p on p.ms_id=m.id") 
		sql.as(membershipPersonParser *)
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
