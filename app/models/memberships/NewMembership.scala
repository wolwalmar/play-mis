
package models.memberships

import java.util.Date

case class ChangePerson(salutation: String, title: String, firstname: String, lastname: String, birthday: java.util.Date)
case class ChangeAddress(street: String, number: String, zip: String, city: String)
case class BrandNewMembership(membershipid: String,begin_ms: Date, end_ms: Option[Date], contrib: String)
case class ChangeMembership(begin_ms: Date, end_ms: Date, contrib: String)
case class ChangeContact(phoneHome: String, phoneOffice: String, mobile: String, email: String )
case class ChangeLegalProtectionInsurance(begin_rsv: Option[Date], end_rsv: Option[Date], contrib_rsv: Option[String])
case class NewMembership(
		membership: BrandNewMembership,
		person: ChangePerson,
		address: ChangeAddress,
		contact: ChangeContact,
		rsv: ChangeLegalProtectionInsurance,
/*		begin_rsv: java.util.Date,
		end_rsv: java.util.Date,
		contrib_rsv: String,
*/		withLPI: Boolean,
		withadmissionfee: Boolean );
