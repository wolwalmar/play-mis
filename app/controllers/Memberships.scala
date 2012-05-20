package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._
import models.memberships._
import models.persons._
import models.finance._

object Memberships extends Controller {
	val newMembershipForm: Form[NewMembership] = Form(
		mapping(
      		"membershipid" -> text,
          "begin" -> date,
      		"name" -> text
      	) 
		(NewMembership.apply)(NewMembership.unapply)
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
        val ms_ref = Membership.insert(new Membership(0,newmembership.membershipid.toLong,newmembership.begin))
        Person.insert(new Person(0,newmembership.name,ms_ref))
        Account.insert(new Account(0,"Beitrag",new java.util.Date,new java.math.BigDecimal("-63.90"),ms_ref))
        Ok(views.html.index(""))
      }
    )
  }

}
