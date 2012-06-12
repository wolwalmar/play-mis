package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json._

import java.util.Date
import views._
import models.memberships._
import models.persons._
import models.finance._

case class ChangePerson(salutation: String, title: String, firstname: String, lastname: String, birthday: java.util.Date)
case class ChangeAddress(street: String, number: String, zip: String, city: String)
case class ChangeMembership(begin_ms: Date, end_ms: Date, contrib: String)

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

  val changePersonForm: Form[ChangePerson] = Form(
    mapping(
          "salutation" -> text,
          "title" -> text,
          "firstname" -> text,
          "lastname" -> text,
          "birthday" -> date
        ) 
    (ChangePerson.apply)(ChangePerson.unapply)
  )

  val changeAddressForm: Form[ChangeAddress] = Form(
    mapping(
          "street" -> text,
          "number" -> text,
          "zip" -> text,
          "city" -> text
        ) 
    (ChangeAddress.apply)(ChangeAddress.unapply)
  )

  val changeMembershipForm: Form[ChangeMembership] = Form(
    mapping(
          "begin_ms" -> date,
          "end_ms" -> date,
          "contrib" -> text
        ) 
    (ChangeMembership.apply)(ChangeMembership.unapply)
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

  def douploadrsv =  Action(parse.multipartFormData) { request =>
    request.body.file("fileToUpload").map { picture =>
      import java.io.File
      val filename = picture.filename 
      val contentType = picture.contentType
      picture.ref.moveTo(new File("/temp/picture"))
      Membership.loadFromFile("/temp/picture")
      Ok("File uploaded")
    }.getOrElse {
      Redirect(routes.Application.index).flashing(
        "error" -> "Missing file"
    ) }
  }

  def changePerson(membershipId: Long) = Action { implicit request =>
    changePersonForm.bindFromRequest.fold(
      errors => { println("error"); BadRequest("error") },
      changes => {
          println(membershipId)
          val ms = Membership.findByMembershipId(membershipId)
          val person = Person.findByMsRef(ms.id)
          Person.update(
            new Person(
              person.id, 
              changes.salutation,
              changes.title,
              changes.firstname,
              changes.lastname,
              changes.birthday,
              ms.membershipId))
          val df = new java.text.SimpleDateFormat("yyyy-MM-dd")
          Ok(Json.toJson(
              Map(
                "salutation"->Json.toJson(changes.salutation),
                "title"->Json.toJson(changes.title),
                "firstname"->Json.toJson(changes.firstname),
                "lastname"->Json.toJson(changes.lastname),
                "birthday"->Json.toJson(df.format(changes.birthday))
              )))
          })
  }

  def changeAddress(membershipId: Long) = Action { implicit request =>
    changeAddressForm.bindFromRequest.fold(
      errors => { println("error"); BadRequest("error") },
      changes => {
          println(membershipId)
          val ms = Membership.findByMembershipId(membershipId)
          val address = Address.findByMsRef(ms.id)
          Address.update(
            new Address(
              address.id, 
              changes.street,
              changes.number,
              changes.zip,
              changes.city,
              ms.membershipId,
              address.rsv_ref))
          val df = new java.text.SimpleDateFormat("yyyy-MM-dd")
          Ok(Json.toJson(
              Map(
                "street"->Json.toJson(changes.street),
                "number"->Json.toJson(changes.number),
                "zip"->Json.toJson(changes.zip),
                "city"->Json.toJson(changes.city)
              )))
          })
  }

  def changeMembership(membershipId: Long) = Action { implicit request =>
    changeMembershipForm.bindFromRequest.fold(
      errors => { println("error"); BadRequest("error") },
      changes => {
          println(membershipId)
          val ms = Membership.findByMembershipId(membershipId)
          Membership.update(
            new Membership(
              ms.id, 
              membershipId,
              changes.begin_ms,
              changes.end_ms,
              changes.contrib.toInt))
          val df = new java.text.SimpleDateFormat("yyyy-MM-dd")
          Ok(Json.toJson(
              Map(
                "begin_ms"->Json.toJson(df.format(changes.begin_ms)),
                "end_ms"->Json.toJson(df.format(changes.end_ms)),
                "contrib"->Json.toJson(changes.contrib)
              )))
          })
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
