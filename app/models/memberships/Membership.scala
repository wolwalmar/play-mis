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
import models.finance._

case class Membership(
	id: Long,
	membershipId: Long,
	begin_ms: Date,
	end_ms: Date,
	contrib: Int
)

object Membership {
	val membershipParser: RowParser[Membership] = {
		long("id") ~ 
		long("ms_id") ~
		date("begin_ms") ~
		date("end_ms") ~
		int("contrib") map {
			case id ~ membershipId ~ begin_ms ~ end_ms ~ contrib => Membership(id,membershipId,begin_ms,end_ms,contrib)
		}
	}

	val membershipIdParser: RowParser[Long] = {
		long("next") map {
			case next => next
		}
	}

	val membershipsParser: ResultSetParser[List[Membership]] = {
		membershipParser *
	}

	val membershipIdRSParser: ResultSetParser[List[Long]] = {
		membershipIdParser *
	}

	def membershipPersonParser: RowParser[(Membership,Person)] = {
		membershipParser ~ Person.personParser map (flatten)
	}

	def membershipPersonAccountParser: RowParser[(Membership,Person,Account)] = {
		membershipParser ~ Person.personParser ~ Account.accountParser map (flatten)
	}

	def membershipPersonAddressParser: RowParser[(Membership,Person,Address)] = {
		membershipParser ~ Person.personParser ~ Address.addressParser map (flatten)
	}

	def findAll: List[Membership] = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from membership")
		sql.as(membershipsParser)
	}

	def findAllMembershipsPersons: List[(Membership,Person)] = DB.withConnection {
		implicit connection => 
		val sql = SQL("select m.*,p.* from membership m join person p on p.ms_ref=m.id") 
		sql.as(membershipPersonParser *)
	}

	def findMembershipPersonById(id: Long): (Membership,Person,Address,List[Account],Option[LegalProtectionInsurance]) = DB.withConnection {
		implicit connection => 
		val sql = SQL(
			"select "+
				"m.*,p.*,a.* "+
			"from "+
				"membership m "+
				"join person p on p.ms_ref=m.id "+
				"join address a on a.ms_ref=m.id "+
			"where m.id={id}").on("id" -> id)  
		val membershipPerson = sql.as(membershipPersonAddressParser *).head
		(membershipPerson._1,
			membershipPerson._2,
			membershipPerson._3,
			findAllPostingsById(id),
			membershipPerson._3.rsv_ref match {
				case Some(ref) => Option(LegalProtectionInsurance.findLegalProtectionById(ref))
				case None => None
			})
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

	def findAllPostingsById(membershipId: Long): List[Account] = DB.withConnection {
		implicit connection =>
		val sql = SQL("select * from account where ms_ref={membershipId}").on("membershipId" -> membershipId)
		sql.as(Account.accountParser *)
	}

	def insert(membership:Membership) = {
		DB.withConnection {
			implicit connection =>
			val next = nextSeqNum

			println(next)

			SQL("""insert into
						membership
					(id,ms_id,begin_ms,end_ms,contrib) values
					({id},{ms_id},{begin_ms},{end_ms},{contrib})
				""").on(
					"id" -> next,
					"ms_id" -> membership.membershipId,
					"begin_ms" -> membership.begin_ms,
					"end_ms" -> membership.end_ms,
					"contrib" -> membership.contrib
				).executeUpdate()
			next
		}
	}

	def nextSeqNum: Long = DB.withConnection {
		implicit connection =>
			return SQL("select ms_id_seq.nextval as next from dual").as(membershipIdRSParser).head
	}
}
