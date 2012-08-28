package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.administration.ImportDMBFile

object Application extends Controller {
  val importForm: Form[ImportDMBFile] = Form(
    mapping(
          "filename" -> text
        ) (ImportDMBFile.apply)(ImportDMBFile.unapply)
  )
  
  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

  def uploadForm =  Action { implicit request =>
  	Ok(views.html.administration.importDMBFile(importForm))
  }

  def upload =  Action { implicit request =>
      importForm.bindFromRequest().fold(
        hasErrors = { form =>
            Redirect(routes.Application.uploadForm).flashing(Flash(form.data) +       
              ("error" -> "Fehler"))
      },
      success = { newProduct =>
          // todo: operate
          Redirect(routes.Memberships.list()).flashing("success" -> "Datei wurde verarbeitet") 
      })

  }
}
