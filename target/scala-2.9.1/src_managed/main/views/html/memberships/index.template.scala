
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
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Seq[scala.Tuple2[models.memberships.Membership, models.persons.Person]],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(memberships: Seq[(models.memberships.Membership,models.persons.Person)]):play.api.templates.Html = {
        _display_ {

Seq(format.raw/*1.75*/("""

<h1>Memberships</h1>
<ul>
"""),_display_(Seq(/*5.2*/for(membership <- memberships) yield /*5.32*/ {_display_(Seq(format.raw/*5.34*/("""
	<li>
		"""),_display_(Seq(/*7.4*/membership/*7.14*/._1.membershipId)),format.raw/*7.30*/("""
		"""),_display_(Seq(/*8.4*/membership/*8.14*/._2.name)),format.raw/*8.22*/("""
		"""),_display_(Seq(/*9.4*/membership/*9.14*/._1.begin)),format.raw/*9.23*/("""
		<a href=""""),_display_(Seq(/*10.13*/controllers/*10.24*/.routes.Memberships.details(membership._1.id))),format.raw/*10.69*/("""">details</a>
	</li>
""")))})),format.raw/*12.2*/("""
</ul>
<a href=""""),_display_(Seq(/*14.11*/controllers/*14.22*/.routes.Memberships.form)),format.raw/*14.46*/("""">add</a>"""))}
    }
    
    def render(memberships:Seq[scala.Tuple2[models.memberships.Membership, models.persons.Person]]) = apply(memberships)
    
    def f:((Seq[scala.Tuple2[models.memberships.Membership, models.persons.Person]]) => play.api.templates.Html) = (memberships) => apply(memberships)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed May 16 21:53:38 CEST 2012
                    SOURCE: /Users/prog/Documents/work/play-work/play-mis/app/views/memberships/index.scala.html
                    HASH: 84af93f7245cc3d918728d126eaa5b08ff4a7d40
                    MATRIX: 582->1|727->74|785->103|830->133|864->135|903->145|921->155|958->171|991->175|1009->185|1038->193|1071->197|1089->207|1119->216|1163->229|1183->240|1250->285|1303->307|1351->324|1371->335|1417->359
                    LINES: 19->1|22->1|26->5|26->5|26->5|28->7|28->7|28->7|29->8|29->8|29->8|30->9|30->9|30->9|31->10|31->10|31->10|33->12|35->14|35->14|35->14
                    -- GENERATED --
                */
            