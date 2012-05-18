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
case class Account(id: Long, text: String, date: Date, amount: BigDecimal)

object Account {
	val accountParser: RowParser[Account] = {
		long("id") ~ str("text") ~ date("date") ~ get[BigDecimal]("amount") map {
			case id ~ text ~ date ~ amount => Account(id, text, date, amount)
		}
	}
}
