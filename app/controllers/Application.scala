package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.administration.ImportDMBFile
import models.memberships.Membership

object Application extends Controller {
  val importForm: Form[ImportDMBFile] = Form(
    mapping(
          "filename" -> text
        ) (ImportDMBFile.apply)(ImportDMBFile.unapply)
  )
  
  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }
/*
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
      Redirect(routes.Application.uploadForm).flashing( "error" -> "Missing file" ) 
    }
  }
*/}

