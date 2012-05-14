
package views.html.memberships

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
object details extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[models.memberships.Membership,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(membership: models.memberships.Membership):play.api.templates.Html = {
        _display_ {

Seq(format.raw/*1.45*/("""

<h1>Membership No.: """),_display_(Seq(/*3.22*/membership/*3.32*/.membershipId)),format.raw/*3.45*/("""<h1>
"""))}
    }
    
    def render(membership:models.memberships.Membership) = apply(membership)
    
    def f:((models.memberships.Membership) => play.api.templates.Html) = (membership) => apply(membership)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon May 14 10:15:38 CEST 2012
                    SOURCE: C:/Users/marquardt/Documents/play_work/play-mis/app/views/memberships/details.scala.html
                    HASH: 40e32e9c5378a621e69466290d9ea0ae86594af1
                    MATRIX: 542->1|657->44|710->67|728->77|762->90
                    LINES: 19->1|22->1|24->3|24->3|24->3
                    -- GENERATED --
                */
            