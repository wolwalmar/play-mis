
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
object form extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[Form[models.memberships.NewMembership],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(newMembershipForm: Form[models.memberships.NewMembership]):play.api.templates.Html = {
        _display_ {import helper._

import helper.twitterBootstrap._


Seq(format.raw/*1.61*/("""

"""),format.raw/*5.1*/("""
"""),_display_(Seq(/*6.2*/main("New")/*6.13*/ {_display_(Seq(format.raw/*6.15*/("""
    """),_display_(Seq(/*7.6*/helper/*7.12*/.form(action = routes.Memberships.submit)/*7.53*/ {_display_(Seq(format.raw/*7.55*/("""
        """),_display_(Seq(/*8.10*/helper/*8.16*/.inputText(newMembershipForm("membershipid"),'_error -> newMembershipForm.globalError))),format.raw/*8.102*/(""" 
        """),_display_(Seq(/*9.10*/helper/*9.16*/.inputDate(newMembershipForm("begin")))),format.raw/*9.54*/("""   
        """),_display_(Seq(/*10.10*/helper/*10.16*/.inputText(newMembershipForm("name")))),format.raw/*10.53*/("""   
        <input type="submit" class="btn primary" value="Sign Up">
        <a href=""""),_display_(Seq(/*12.19*/routes/*12.25*/.Application.index)),format.raw/*12.43*/("""" class="btn">Cancel</a>
    """)))})),format.raw/*13.6*/("""
""")))})))}
    }
    
    def render(newMembershipForm:Form[models.memberships.NewMembership]) = apply(newMembershipForm)
    
    def f:((Form[models.memberships.NewMembership]) => play.api.templates.Html) = (newMembershipForm) => apply(newMembershipForm)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Mon May 14 21:49:38 CEST 2012
                    SOURCE: /Users/prog/Documents/work/play-work/play-mis/app/views/memberships/form.scala.html
                    HASH: 9112a066893dc183ff5344be64007eaad3d6f73a
                    MATRIX: 548->1|729->60|757->113|788->115|807->126|841->128|876->134|890->140|939->181|973->183|1013->193|1027->199|1135->285|1176->296|1190->302|1249->340|1293->353|1308->359|1367->396|1486->484|1501->490|1541->508|1602->538
                    LINES: 19->1|25->1|27->5|28->6|28->6|28->6|29->7|29->7|29->7|29->7|30->8|30->8|30->8|31->9|31->9|31->9|32->10|32->10|32->10|34->12|34->12|34->12|35->13
                    -- GENERATED --
                */
            