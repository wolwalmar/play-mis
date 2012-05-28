
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
		city: String);
