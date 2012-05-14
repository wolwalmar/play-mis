
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
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Seq[models.memberships.Membership],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(memberships: Seq[models.memberships.Membership]):play.api.templates.Html = {
        _display_ {

Seq(format.raw/*1.51*/("""

<h1>Memberships</h1>
<ul>
"""),_display_(Seq(/*5.2*/for(membership <- memberships) yield /*5.32*/ {_display_(Seq(format.raw/*5.34*/("""
	<li>
		"""),_display_(Seq(/*7.4*/membership/*7.14*/.membershipId)),format.raw/*7.27*/("""
		"""),_display_(Seq(/*8.4*/membership/*8.14*/.begin)),format.raw/*8.20*/("""
		<a href=""""),_display_(Seq(/*9.13*/controllers/*9.24*/.routes.Memberships.details(membership.id))),format.raw/*9.66*/("""">details</a>
	</li>
""")))})),format.raw/*11.2*/("""
</ul>
<a href=""""),_display_(Seq(/*13.11*/controllers/*13.22*/.routes.Memberships.form)),format.raw/*13.46*/("""">add</a>"""))}
    }
    
    def render(memberships:Seq[models.memberships.Membership]) = apply(memberships)
    
    def f:((Seq[models.memberships.Membership]) => play.api.templates.Html) = (memberships) => apply(memberships)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon May 14 10:15:38 CEST 2012
                    SOURCE: C:/Users/marquardt/Documents/play_work/play-mis/app/views/memberships/index.scala.html
                    HASH: 9faace541a2b13ce225b4d79879b81c03890d3c7
                    MATRIX: 545->1|666->50|724->79|769->109|803->111|842->121|860->131|894->144|927->148|945->158|972->164|1015->177|1034->188|1097->230|1150->252|1198->269|1218->280|1264->304
                    LINES: 19->1|22->1|26->5|26->5|26->5|28->7|28->7|28->7|29->8|29->8|29->8|30->9|30->9|30->9|32->11|34->13|34->13|34->13
                    -- GENERATED --
                */
            