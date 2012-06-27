
package models.memberships

import java.util.Date

case class ChangePerson(salutation: String, title: String, firstname: String, lastname: String, birthday: java.util.Date)
case class ChangeAddress(street: String, number: String, zip: String, city: String)
case class BrandNewMembership(membershipid: String,begin_ms: Date, end_ms: Date, contrib: String)
case class ChangeMembership(begin_ms: Date, end_ms: Date, contrib: String)

case class NewMembership(
		// membershipid: String, 
		// begin_ms: java.util.Date, 
		// end_ms: java.util.Date,
		// contrib: String,
		membership: BrandNewMembership,
		person: ChangePerson,
		address: ChangeAddress,
		// salutation: String,
		// title: String,
		// firstname: String, 
		// lastname: String,
		// birthday: java.util.Date, 
		// street: String,
		// number: String,
		// zip: String,
		// city: String,
		begin_rsv: java.util.Date,
		end_rsv: java.util.Date,
		contrib_rsv: String,
		withLPI: Boolean,
		withadmissionfee: Boolean );
