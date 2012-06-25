package models.persons

import java.util.Date
import anorm._
import SqlParser._
import anorm.Row
import anorm.SQL
import anorm.SqlQuery

import play.api.db.DB
import play.api.Play.current

case class Contact(
		id: Long, 
		phoneHome: String, 
		phoneOffice: String, 
		mobile: String, 
		email: String, 
		ms_ref: Long)

object Contact {
	val contactParser: RowParser[Contact] = {
		long("id") ~ 
		str("phoneHome") ~
		str("phoneOffice") ~ 
		str("mobile") ~ 
		str("email")  ~ 
		long("ms_ref") map {
			case id ~ 
				phoneHome ~ 
				phoneOffice ~ 
				mobile ~ 
				email ~ 
				ms_ref=> Contact(id, phoneHome, phoneOffice, mobile, email, ms_ref)
			case _ => Contact(0L, "", "", "", "", 0L)
		}
	}

	def findByMsRef(ms_ref: Long): Contact = DB.withConnection {
		implicit connection => 
		val sql = SQL("select * from contact where ms_ref={ms_ref}").on("ms_ref" -> ms_ref)
		sql.as(contactParser *).head
	}

	def insert(c: Contact): Boolean = {
		DB.withConnection {
			implicit connection =>
			SQL("""insert into
						contact
					(phoneHome,phoneOffice,mobile,email,ms_ref) values
					({phoneHome},{phoneOffice},{mobile},{email},{ms_ref})
				""").on(
					"phoneHome" -> c.phoneHome,
					"phoneOffice" -> c.phoneOffice,
					"mobile" -> c.mobile,
					"email" -> c.email,
					"ms_ref" -> c.ms_ref
				).executeUpdate() == 1
		}
	}

	def update(c: Contact): Boolean = 
		DB.withConnection {
			implicit connection =>
			SQL("""update
						contact
					set 
						phoneHome={phoneHome},
						phoneOffice={phoneOffice},
						mobile={mobile},
						email={email}
					where
						id={id}""")
			.on(		
				"phoneHome" -> c.phoneHome,
				"phoneOffice" -> c.phoneOffice,
				"mobile" -> c.mobile,
				"email" -> c.email,
				"id" -> c.id).executeUpdate == 1
		}
}

