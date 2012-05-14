
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(message: String):play.api.templates.Html = {
        _display_ {

Seq(format.raw/*1.19*/("""

"""),_display_(Seq(/*3.2*/main("Welcome to Play 2.0")/*3.29*/ {_display_(Seq(format.raw/*3.31*/("""
    
    <h2>Sign up form</h2>
    
    <p>
        Demonstrate a classical sign up form.
    </p>
    
    <p>
        <a class="btn" href=""""),_display_(Seq(/*12.31*/routes/*12.37*/.Memberships.list())),format.raw/*12.56*/("""">View sample »</a>
    </p>
        
""")))})),format.raw/*15.2*/("""    
"""))}
    }
    
    def render(message:String) = apply(message)
    
    def f:((String) => play.api.templates.Html) = (message) => apply(message)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon May 14 10:15:38 CEST 2012
                    SOURCE: C:/Users/marquardt/Documents/play_work/play-mis/app/views/index.scala.html
                    HASH: 9ac4b321a3984172848f2d22efbb01705e8e84fb
                    MATRIX: 505->1|594->18|626->21|661->48|695->50|869->193|884->199|925->218|995->257
                    LINES: 19->1|22->1|24->3|24->3|24->3|33->12|33->12|33->12|36->15
                    -- GENERATED --
                */
            