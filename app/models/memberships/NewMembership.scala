
package models.memberships

case class NewMembership(
		membershipid: String, 
		begin: java.util.Date, 
		firstname: String, 
		lastname: String, 
		street: String);
