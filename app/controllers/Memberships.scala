package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json._
import play.api.data.format.Formats._

import java.util.Date
import views._
import models.memberships._
import models.persons._
import models.finance._



object Memberships extends Controller {
  lazy val dateFormat = "dd.MM.yyyy"
	val newMembershipForm: Form[NewMembership] = Form(
    // !!! no CamelCase !!!
		mapping(
          "membership" -> mapping(
        		"membershipid" -> of[Long],
            "begin_ms" -> date(dateFormat),
            "end_ms" -> optional(date(dateFormat)),
            "contrib" -> number )(BrandNewMembership.apply)(BrandNewMembership.unapply),
          "person" -> mapping(
            "salutation" -> text,
            "title" -> text,
        		"firstname" -> text,
            "lastname" -> text,
            "birthday" -> optional(date(dateFormat)))(ChangePerson.apply)(ChangePerson.unapply),
          "address" -> mapping(
            "street" -> text,
            "number" -> text,
            "zip" -> text,
            "city" -> text)(ChangeAddress.apply)(ChangeAddress.unapply),
          "contact" -> mapping(
            "phonehome" -> text, 
            "phoneoffice" -> text, 
            "mobile" -> text, 
            "email" -> text)(ChangeContact.apply)(ChangeContact.unapply),
          "rsv" -> mapping(
            "begin_rsv" -> optional(date(dateFormat)),
            "end_rsv" -> optional(date(dateFormat)),
            "contrib_rsv" -> optional(text))(ChangeLegalProtectionInsurance.apply)(ChangeLegalProtectionInsurance.unapply),
          "withLPI" -> boolean,
          "withadmissionfee" -> boolean
      	) (NewMembership.apply)(NewMembership.unapply)
	)

  val changePersonForm: Form[ChangePerson] = Form(
    mapping(
          "salutation" -> text,
          "title" -> text,
          "firstname" -> text,
          "lastname" -> text,
          "birthday" -> optional(date(dateFormat))
        ) (ChangePerson.apply)(ChangePerson.unapply)
  )

  val changeAddressForm: Form[ChangeAddress] = Form(
    mapping(
          "street" -> text,
          "number" -> text,
          "zip" -> text,
          "city" -> text
        ) (ChangeAddress.apply)(ChangeAddress.unapply)
  )

  val changeRSVForm: Form[ChangeLegalProtectionInsurance] = Form(
    mapping(
          "begin_rsv" -> optional(date(dateFormat)),
          "end_rsv" -> optional(date(dateFormat)),
          "contrib_rsv" -> optional(text)
        ) (ChangeLegalProtectionInsurance.apply)(ChangeLegalProtectionInsurance.unapply)
  )
  val changeContactForm: Form[ChangeContact] = Form(
    mapping(
          "phoneHome" -> text, 
          "phoneOffice" -> text, 
          "mobile" -> text, 
          "email" -> text
        ) (ChangeContact.apply)(ChangeContact.unapply)
  )

  val changeMembershipForm: Form[ChangeMembership] = Form(
    mapping(
          "begin_ms_dialog" -> date(dateFormat),
          "end_ms_dialog" -> optional(date(dateFormat)),
          "contrib_dialog" -> text
        ) (ChangeMembership.apply)(ChangeMembership.unapply)
  )

 	def form = Action { implicit request =>
    val theForm = if (flash.get("error").isDefined)   
        newMembershipForm.bind(flash.data)
      else
        newMembershipForm
 
    Ok(html.memberships.form(theForm,
      Map("salutations" -> List("Frau","Herr","Eheleute"), 
          "titles" -> List("","Dr.", "Prof. Dr.", "Dipl.Ing."),
          "mscontribgrps" -> List("0","1","2","3"))));
  }

	def list(page: Int = 1) = Action { implicit request =>
		val memberships = Membership.findAllMembershipsPersons
		Ok(views.html.memberships.index(memberships))
	}

	def details(id: Long) = Action { implicit request =>
		val membershipPerson = Membership.findMembershipPersonById(id)
		Ok(views.html.memberships.details(membershipPerson,changeMembershipForm,changePersonForm,Map("salutations" -> List("Frau","Herr","Eheleute"), "titles" -> List("","Dr.", "Prof. Dr.", "Dipl.Ing."))))
	}

	def edit(id: Long) = TODO

	def update(id: Long) = TODO

  def validateMsId(membershipId: Long) = Action { implicit request =>
    println(membershipId)
    Ok(Json.toJson(Map("exist" -> Json.toJson(Membership.findByMembershipIdOption(membershipId).map {m: Membership => 1}.getOrElse(0)))))
  }

  def changePerson(membershipId: Long) = Action { implicit request =>
    changePersonForm.bindFromRequest.fold(
      errors => { println("error"); BadRequest("error") },
      changes => {
          println(membershipId+ ":" + changes.birthday)
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
          val df = new java.text.SimpleDateFormat("dd.MM.yyyy")
          Ok(Json.toJson(
              Map(
                "salutation"->Json.toJson(changes.salutation),
                "title"->Json.toJson(changes.title),
                "firstname"->Json.toJson(changes.firstname),
                "lastname"->Json.toJson(changes.lastname),
                "birthday"->Json.toJson(changes.birthday.map { d: Date => df.format(d) }. getOrElse(""))
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

  def changeLegalProtectionInsurance(membershipId: Long) = Action { implicit request =>
    changeRSVForm.bindFromRequest.fold(
      errors => { println("error"); BadRequest("error") },
      changes => {
          println(membershipId)
          val ms = Membership.findByMembershipId(membershipId)
          val address = Address.findByMsRef(ms.id)

          address.rsv_ref.map { ref =>
            val lpi = LegalProtectionInsurance.findById(ref)

            changes match {
              case ChangeLegalProtectionInsurance(None,_,_) => 
                      Address.removeLPI(address)
                      LegalProtectionInsurance.delete(lpi)
              case ChangeLegalProtectionInsurance(Some(begin_rsv),end_rsv,Some(contrib_rsv)) =>
                 LegalProtectionInsurance.update(
                    new LegalProtectionInsurance(
                      lpi.id, 
                      begin_rsv,
                      end_rsv,
                      contrib_rsv.toInt))
          }
          val df = new java.text.SimpleDateFormat("yyyy-MM-dd")
            Ok(Json.toJson(
              Map(
                "begin_rsv"->Json.toJson(df.format(changes.begin_rsv)),
                "end_rsv"->Json.toJson(df.format(changes.end_rsv)),
                "contrib_rsv"->Json.toJson(changes.contrib_rsv)
              )))
          }.getOrElse(BadRequest("RSV not found"))
        })
  }

  def changeContact(membershipId: Long) = Action { implicit request =>
    changeContactForm.bindFromRequest.fold(
      errors => { println("error"); BadRequest("error") },
      changes => {
          println(membershipId)
          val ms = Membership.findByMembershipId(membershipId)
          val contact = Contact.findByMsRef(ms.id)
          Contact.update(
            new Contact(
              contact.id, 
              changes.phoneHome,
              changes.phoneOffice,
              changes.mobile,
              changes.email,
              ms.membershipId))

          Ok(Json.toJson(
              Map(
                "phoneHome"->Json.toJson(changes.phoneHome),
                "phoneOffice"->Json.toJson(changes.phoneOffice),
                "mobile"->Json.toJson(changes.mobile),
                "email"->Json.toJson(changes.email)
              )))
          })
  }

  def changeMembership(membershipId: Long) = Action { implicit request =>
    changeMembershipForm.bindFromRequest.fold(
      formWithErrors => { println(formWithErrors); BadRequest("error") },
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
          val df = new java.text.SimpleDateFormat("dd.MM.yyyy")
          Ok(Json.toJson(
              Map(
                "begin_ms"->Json.toJson(df.format(changes.begin_ms)),
                "end_ms"->Json.toJson(changes.end_ms.map { d: Date => df.format(d) }. getOrElse("")),
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
      hasErrors = { form =>
            Redirect(routes.Memberships.form).flashing(Flash(form.data) + ("error" -> "Fehler")) },
     success = { newmembership => {
          val ms_ref = Membership.insert(new Membership(0,
                            newmembership.membership.membershipid.toLong,
                            newmembership.membership.begin_ms,
                            newmembership.membership.end_ms,
                            newmembership.membership.contrib.toInt))
          Person.insert(new Person(0,
                            newmembership.person.salutation,
                            newmembership.person.title,
                            newmembership.person.firstname,
                            newmembership.person.lastname,
                            newmembership.person.birthday,
                            ms_ref))
          val rsv_ref = newmembership.rsv match {
              case ChangeLegalProtectionInsurance(None,_,_) => 
                0
              case ChangeLegalProtectionInsurance(Some(begin_rsv),end_rsv,Some(contrib_rsv)) =>
                if( newmembership.withLPI) {
                  LegalProtectionInsurance.insert( new LegalProtectionInsurance(0,
                              begin_rsv,
                              end_rsv,
                              contrib_rsv.toInt))
                  }
              else 0
          }
        
          Address.insert(new Address(0,
                            newmembership.address.street,
                            newmembership.address.number,
                            newmembership.address.zip,
                            newmembership.address.city,
                            ms_ref,
                            if( newmembership.withLPI ) Some(rsv_ref) else None))

          Contact.insert(new Contact(0,
                            newmembership.contact.phoneHome,
                            newmembership.contact.phoneOffice,
                            newmembership.contact.mobile,
                            newmembership.contact.email,
                            ms_ref))

          Account.insert(new Account(0,"Beitrag",new java.util.Date,new java.math.BigDecimal("-63.90"),ms_ref))
          if( newmembership.withadmissionfee ) 
            Account.insert(
              new Account(0,"Aufnahmegebühr",new java.util.Date,new java.math.BigDecimal("-15.00"),ms_ref))
          Redirect(routes.Memberships.details(ms_ref)).flashing("success" -> ("Mitglied "+ newmembership.person.lastname+", "+newmembership.person.firstname+" wurde angelegt"))
        }
      }
    )
  }

}
