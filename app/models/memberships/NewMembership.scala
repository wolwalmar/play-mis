
package models.memberships

case class NewMembership(
		membershipid: String, 
		begin_ms: java.util.Date, 
		end_ms: java.util.Date,
		contrib: String,
		salutation: String,
		title: String,
		firstname: String, 
		lastname: String,
		birthday: java.util.Date, 
		street: String,
		number: String,
		zip: String,
		city: String,
		begin_rsv: java.util.Date,
		end_rsv: java.util.Date,
		contrib_rsv: Int,
		withLPI: Boolean);
