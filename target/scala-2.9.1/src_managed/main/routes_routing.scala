// @SOURCE:C:/Users/marquardt/Documents/play_work/play-mis/conf/routes
// @HASH:c22de58141a18da85ef0ca546b0df62446565b6a
// @DATE:Mon May 14 10:15:37 CEST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString

object Routes extends Router.Routes {


// @LINE:6
val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart("/"))))
                    

// @LINE:8
val controllers_Memberships_form1 = Route("GET", PathPattern(List(StaticPart("/newmembership"))))
                    

// @LINE:9
val controllers_Memberships_list2 = Route("GET", PathPattern(List(StaticPart("/memberships"))))
                    

// @LINE:10
val controllers_Memberships_details3 = Route("GET", PathPattern(List(StaticPart("/membership/"),DynamicPart("id", """[^/]+"""))))
                    

// @LINE:11
val controllers_Memberships_edit4 = Route("GET", PathPattern(List(StaticPart("/membership/"),DynamicPart("id", """[^/]+"""),StaticPart("/edit"))))
                    

// @LINE:12
val controllers_Memberships_update5 = Route("PUT", PathPattern(List(StaticPart("/membership/"),DynamicPart("id", """[^/]+"""))))
                    

// @LINE:13
val controllers_Memberships_submit6 = Route("POST", PathPattern(List(StaticPart("/memberships"))))
                    

// @LINE:16
val controllers_Assets_at7 = Route("GET", PathPattern(List(StaticPart("/assets/"),DynamicPart("file", """.+"""))))
                    
def documentation = List(("""GET""","""/""","""controllers.Application.index"""),("""GET""","""/newmembership""","""controllers.Memberships.form"""),("""GET""","""/memberships""","""controllers.Memberships.list(page:Int ?= 1)"""),("""GET""","""/membership/$id<[^/]+>""","""controllers.Memberships.details(id:Long)"""),("""GET""","""/membership/$id<[^/]+>/edit""","""controllers.Memberships.edit(id:Long)"""),("""PUT""","""/membership/$id<[^/]+>""","""controllers.Memberships.update(id:Long)"""),("""POST""","""/memberships""","""controllers.Memberships.submit"""),("""GET""","""/assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""))
             
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(_root_.controllers.Application.index, HandlerDef(this, "controllers.Application", "index", Nil))
   }
}
                    

// @LINE:8
case controllers_Memberships_form1(params) => {
   call { 
        invokeHandler(_root_.controllers.Memberships.form, HandlerDef(this, "controllers.Memberships", "form", Nil))
   }
}
                    

// @LINE:9
case controllers_Memberships_list2(params) => {
   call(params.fromQuery[Int]("page", Some(1))) { (page) =>
        invokeHandler(_root_.controllers.Memberships.list(page), HandlerDef(this, "controllers.Memberships", "list", Seq(classOf[Int])))
   }
}
                    

// @LINE:10
case controllers_Memberships_details3(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(_root_.controllers.Memberships.details(id), HandlerDef(this, "controllers.Memberships", "details", Seq(classOf[Long])))
   }
}
                    

// @LINE:11
case controllers_Memberships_edit4(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(_root_.controllers.Memberships.edit(id), HandlerDef(this, "controllers.Memberships", "edit", Seq(classOf[Long])))
   }
}
                    

// @LINE:12
case controllers_Memberships_update5(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(_root_.controllers.Memberships.update(id), HandlerDef(this, "controllers.Memberships", "update", Seq(classOf[Long])))
   }
}
                    

// @LINE:13
case controllers_Memberships_submit6(params) => {
   call { 
        invokeHandler(_root_.controllers.Memberships.submit, HandlerDef(this, "controllers.Memberships", "submit", Nil))
   }
}
                    

// @LINE:16
case controllers_Assets_at7(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    
}
    
}
                