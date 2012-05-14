// @SOURCE:C:/Users/marquardt/Documents/play_work/play-mis/conf/routes
// @HASH:c22de58141a18da85ef0ca546b0df62446565b6a
// @DATE:Mon May 14 10:15:37 CEST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString


// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:6
package controllers {

// @LINE:6
class ReverseApplication {
    


 
// @LINE:6
def index() = {
   Call("GET", "/")
}
                                                        

                      
    
}
                            

// @LINE:16
class ReverseAssets {
    


 
// @LINE:16
def at(file:String) = {
   Call("GET", "/assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                        

                      
    
}
                            

// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
class ReverseMemberships {
    


 
// @LINE:8
def form() = {
   Call("GET", "/newmembership")
}
                                                        
 
// @LINE:10
def details(id:Long) = {
   Call("GET", "/membership/" + implicitly[PathBindable[Long]].unbind("id", id))
}
                                                        
 
// @LINE:13
def submit() = {
   Call("POST", "/memberships")
}
                                                        
 
// @LINE:12
def update(id:Long) = {
   Call("PUT", "/membership/" + implicitly[PathBindable[Long]].unbind("id", id))
}
                                                        
 
// @LINE:9
def list(page:Int = 1) = {
   Call("GET", "/memberships" + queryString(List(if(page == 1) None else Some(implicitly[QueryStringBindable[Int]].unbind("page", page)))))
}
                                                        
 
// @LINE:11
def edit(id:Long) = {
   Call("GET", "/membership/" + implicitly[PathBindable[Long]].unbind("id", id) + "/edit")
}
                                                        

                      
    
}
                            
}
                    


// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:6
package controllers.javascript {

// @LINE:6
class ReverseApplication {
    


 
// @LINE:6
def index = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"/"})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:16
class ReverseAssets {
    


 
// @LINE:16
def at = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"/assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
class ReverseMemberships {
    


 
// @LINE:8
def form = JavascriptReverseRoute(
   "controllers.Memberships.form",
   """
      function() {
      return _wA({method:"GET", url:"/newmembership"})
      }
   """
)
                                                        
 
// @LINE:10
def details = JavascriptReverseRoute(
   "controllers.Memberships.details",
   """
      function(id) {
      return _wA({method:"GET", url:"/membership/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id)})
      }
   """
)
                                                        
 
// @LINE:13
def submit = JavascriptReverseRoute(
   "controllers.Memberships.submit",
   """
      function() {
      return _wA({method:"POST", url:"/memberships"})
      }
   """
)
                                                        
 
// @LINE:12
def update = JavascriptReverseRoute(
   "controllers.Memberships.update",
   """
      function(id) {
      return _wA({method:"PUT", url:"/membership/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id)})
      }
   """
)
                                                        
 
// @LINE:9
def list = JavascriptReverseRoute(
   "controllers.Memberships.list",
   """
      function(page) {
      return _wA({method:"GET", url:"/memberships" + _qS([(page == """ +  implicitly[JavascriptLitteral[Int]].to(1) + """ ? null : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("page", page))])})
      }
   """
)
                                                        
 
// @LINE:11
def edit = JavascriptReverseRoute(
   "controllers.Memberships.edit",
   """
      function(id) {
      return _wA({method:"GET", url:"/membership/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/edit"})
      }
   """
)
                                                        

                      
    
}
                            
}
                    


// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:6
package controllers.ref {

// @LINE:6
class ReverseApplication {
    


 
// @LINE:6
def index() = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq())
)
                              

                      
    
}
                            

// @LINE:16
class ReverseAssets {
    


 
// @LINE:16
def at(path:String, file:String) = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]))
)
                              

                      
    
}
                            

// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
class ReverseMemberships {
    


 
// @LINE:8
def form() = new play.api.mvc.HandlerRef(
   controllers.Memberships.form(), HandlerDef(this, "controllers.Memberships", "form", Seq())
)
                              
 
// @LINE:10
def details(id:Long) = new play.api.mvc.HandlerRef(
   controllers.Memberships.details(id), HandlerDef(this, "controllers.Memberships", "details", Seq(classOf[Long]))
)
                              
 
// @LINE:13
def submit() = new play.api.mvc.HandlerRef(
   controllers.Memberships.submit(), HandlerDef(this, "controllers.Memberships", "submit", Seq())
)
                              
 
// @LINE:12
def update(id:Long) = new play.api.mvc.HandlerRef(
   controllers.Memberships.update(id), HandlerDef(this, "controllers.Memberships", "update", Seq(classOf[Long]))
)
                              
 
// @LINE:9
def list(page:Int) = new play.api.mvc.HandlerRef(
   controllers.Memberships.list(page), HandlerDef(this, "controllers.Memberships", "list", Seq(classOf[Int]))
)
                              
 
// @LINE:11
def edit(id:Long) = new play.api.mvc.HandlerRef(
   controllers.Memberships.edit(id), HandlerDef(this, "controllers.Memberships", "edit", Seq(classOf[Long]))
)
                              

                      
    
}
                            
}
                    
                