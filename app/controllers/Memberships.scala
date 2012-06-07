package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._
import models.memberships._
import models.persons._
import models.finance._

case class Change(salutation: String, firstname: String, lastname: String)

object Memberships extends Controller {
	val newMembershipForm: Form[NewMembership] = Form(
		mapping(
      		"membershipid" -> text,
          "begin_ms" -> date,
          "end_ms" -> date,
          "contrib" -> text,
          "salutation" -> text,
          "title" -> text,
      		"firstname" -> text,
          "lastname" -> text,
          "birthday" -> date,
          "street" -> text,
          "number" -> text,
          "zip" -> text,
          "city" -> text,
          "begin_rsv" -> date,
          "end_rsv" -> date,
          "contrib_rsv" -> text,
          "withLPI" -> boolean,
          "withadmissionfee" -> boolean
      	) 
		(NewMembership.apply)(NewMembership.unapply)
	)

  val changeForm: Form[Change] = Form(
    mapping(
          "salutation_input" -> text,
          "firstname_input" -> text,
          "lastname_input" -> text
        ) 
    (Change.apply)(Change.unapply)
  )

	/**
   	 * Display an empty form.
   	*/
 	def form = Action {
    	Ok(html.memberships.form(newMembershipForm));
  	}

	def list(page: Int = 1) = Action {
		val memberships = Membership.findAllMembershipsPersons
		Ok(views.html.memberships.index(memberships))
	}

	def details(id: Long) = Action {
		val membershipPerson = Membership.findMembershipPersonById(id)
		Ok(views.html.memberships.details(membershipPerson))
	}

	def edit(id: Long) = TODO

	def update(id: Long) = TODO

  def changePerson(id: Long) = Action { implicit request =>
    changeForm.bindFromRequest.fold(
      errors => { println("error"); Ok("error") },
      changes => {
          println(changes.firstname);
          Ok(<person><firstname>{changes.firstname}</firstname><lastname>{changes.lastname}</lastname></person>) })
  }

  def changePerson_(id: Long) = Action { implicit request =>
    println("hello")
    Ok("hello")
  }

	  /**
   * Handle form submission.
   */
  def submit = Action { implicit request =>
    newMembershipForm.bindFromRequest.fold(
      // Form has errors, redisplay it
      errors => {println("error"); BadRequest(html.memberships.form(errors))},
      
      // We got a valid User value, display the summary
            // We got a valid User value, display the summary
      newmembership => {
        val ms_ref = Membership.insert(new Membership(0,
                            newmembership.membershipid.toLong,
                            newmembership.begin_ms,
                            newmembership.end_ms,
                            newmembership.contrib.toInt))
        Person.insert(new Person(0,
                            newmembership.salutation,
                            newmembership.title,
                            newmembership.firstname,
                            newmembership.lastname,
                            newmembership.birthday,
                            ms_ref))

        val rsv_ref = if( newmembership.withLPI) {
            println("mit RSV")
            LegalProtectionInsurance.insert( new LegalProtectionInsurance(0,
                              newmembership.begin_rsv,
                              newmembership.end_rsv,
                              newmembership.contrib_rsv.toInt))
          }
          else 0
        
        println("ref "+rsv_ref)
        Address.insert(new Address(0,
                            newmembership.street,
                            newmembership.number,
                            newmembership.zip,
                            newmembership.city,
                            ms_ref,
                            if( newmembership.withLPI ) Some(rsv_ref) else None))
        Account.insert(new Account(0,"Beitrag",new java.util.Date,new java.math.BigDecimal("-63.90"),ms_ref))
        if( newmembership.withadmissionfee ) 
          Account.insert(
            new Account(0,"Aufnahmegebühr",new java.util.Date,new java.math.BigDecimal("-15.00"),ms_ref))
        Ok(views.html.index(""))
      }
    )
  }

}
