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
	end_ms: Option[Date],
	contrib: Int
)

object Membership {
	val membershipParser: RowParser[Membership] = {
		long("id") ~ 
		long("ms_id") ~
		date("begin_ms") ~
		get[Option[Date]]("end_ms") ~
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

	def membershipPersonAddressParser: RowParser[(Membership,Person,Address,Contact)] = {
		membershipParser ~ Person.personParser ~ Address.addressParser ~ Contact.contactParser map (flatten)
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

	def findMembershipPersonById(id: Long): (Membership,Person,Address,List[Account],Option[LegalProtectionInsurance],Contact) = DB.withConnection {
		implicit connection => 
		val sql = SQL(
			"select "+
				"m.*,p.*,a.*,c.* "+
			"from "+
				"membership m "+
				"join person p on p.ms_ref=m.id "+
				"join address a on a.ms_ref=m.id "+
				"left outer join contact c on c.ms_ref=m.id "+
			"where m.id={id}").on("id" -> id)  
		val membershipPerson = sql.as(membershipPersonAddressParser *).head
		(membershipPerson._1,
			membershipPerson._2,
			membershipPerson._3,
			findAllPostingsById(id),
			membershipPerson._3.rsv_ref match {
				case Some(ref) => Option(LegalProtectionInsurance.findLegalProtectionById(ref))
				case None => None
			},
			membershipPerson._4)
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

	def update(m: Membership): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""update
						membership
					set 
						begin_ms={begin_ms},
						end_ms={end_ms},
						contrib={contrib},
						ms_id={ms_id}
					where
						id={id}""")
			.on(		
				"begin_ms" -> m.begin_ms,
				"end_ms" -> m.end_ms,
				"contrib" -> m.contrib,
				"ms_id" -> m.membershipId,
				"id" -> m.id).executeUpdate == 1
		}
	}

	def nextSeqNum: Long = DB.withConnection {
		implicit connection =>
			return SQL("select ms_id_seq.nextval as next from dual").as(membershipIdRSParser).head
	}

	def loadFromFile(filename: String) = {
		def firstnameFrom(name: String) = { val s = name.split(","); if(s.length==2) s(1).trim else "" }
		def lastnameFrom(name: String) = { val s = name.split(","); if(s.length>=1) s(0).trim else "" }
		//ig 1,verein 3,nr 8, name30, name2 30,strasse 30, land 3, plz 5, ort 24, ig 4, von 8, bis 8, ig 3, von 8, bis 8
		val MemberRE = """.{1}(.{3})(.{8})(.{30})(.{30})(.{30})(.{3})(.{5})(.{24}).{4}(.{8})(.{8}).{3}(.{8})(.{8}).*""".r
		val src = scala.io.Source.fromFile(filename)
		val sdf = new java.text.SimpleDateFormat("ddMMyyyy")
		val list = src.getLines.foreach { (line: String) =>
			val MemberRE(vid,mid,name1,name2,street,country,zip,city,begin_ms,end_ms,begin_rs,end_rs) = line
        	val ms_ref = Membership.insert(new Membership(0,
                            mid.toLong,
                            if(begin_ms.trim.length==0) new java.util.Date() else sdf.parse(begin_ms),
                            if(end_ms.trim.length==0) None else Option(sdf.parse(end_ms)),
                            1))
        	Person.insert(new Person(0,
                            "",
                            "",
                            firstnameFrom(name1),
                            lastnameFrom(name1),
                            new java.util.Date(),
                            ms_ref))
        
        	Address.insert(new Address(0,
                            street,
                            "0",
                            zip,
                            city,
                            ms_ref,
                            None))

		}
		src.close()
	}
}
