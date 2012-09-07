package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.administration.ImportDMBFile
import models.memberships.Membership

object FileUploader extends Controller {
  val importForm: Form[ImportDMBFile] = Form(
    mapping(
          "filename" -> text
        ) (ImportDMBFile.apply)(ImportDMBFile.unapply)
  )
  
  def uploadForm =  Action { implicit request =>
  	Ok(views.html.administration.importDMBFile(importForm))
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("importdmb").map { importdmb =>
      import java.io.File
      importdmb.ref.moveTo(new File("/temp/importdmb"),true)
      Membership.loadFromFile("/temp/importdmb")
      Redirect(routes.Memberships.list()).flashing( "success" -> ("Datei "+importdmb.filename+" wurde verarbeitet")) 
    }.getOrElse {
      Redirect(routes.FileUploader.uploadForm).flashing( "error" -> "Missing file" ) 
    }
  }

  def premiumForm = Action { implicit request =>
    Ok(views.html.memberships.premiumAdressForm(importForm))
  }

  def loadPremium = Action(parse.multipartFormData) { implicit request =>
    request.body.file("importdmb").map { importdmb =>
      import java.io.File
      importdmb.ref.moveTo(new File("/temp/importdmb"),true)
      Membership.premiumAdress("/temp/importdmb")
      Redirect(routes.Memberships.premiumAdress) 
    }.getOrElse {
      Redirect(routes.FileUploader.uploadForm).flashing( "error" -> "Missing file" ) 
    }
  }

}

