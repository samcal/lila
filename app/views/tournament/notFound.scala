package views.html
package tournament

import lila.api.Context
import lila.app.templating.Environment._
import lila.app.ui.ScalatagsTemplate._

import controllers.routes

object notFound {

  def apply()(implicit ctx: Context) = tournament.bits.layout(
    title = trans.tournamentNotFound.txt()
  ) {
      div(id := "tournament")(
        div(cls := "content_box small_box faq_page")(
          h1(trans.tournamentNotFound()),
          trans.tournamentDoesNotExist(),
          br(),
          trans.tournamentMayHaveBeenCanceled(),
          br(),
          br(),
          a(href := routes.Tournament.home())(trans.returnToTournamentsHomepage())
        )
      )
    }

}
