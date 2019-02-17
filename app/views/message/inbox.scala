package views.html
package message

import lila.api.Context
import lila.app.templating.Environment._
import lila.app.ui.ScalatagsTemplate._

import controllers.routes

object inbox {

  def apply(me: lila.user.User, threads: lila.common.paginator.Paginator[lila.message.Thread])(implicit ctx: Context) = message.layout(
    title = trans.inbox.txt()
  ) {
      div(id := "lichess_message", cls := "content_box no_padding")(
        div(cls := "head with_actions")(
          h1(trans.inbox()),

          if (threads.nbResults > 0)
            div(cls := "actions")(
            select(cls := "select")(
              option(value := "")("Select"),
              option(value := "all")("All"),
              option(value := "none")("None"),
              option(value := "unread")("Unread"),
              option(value := "read")("Read")
            ),
            select(cls := "action")(
              option(value := "")("Do"),
              option(value := "unread")("Mark as unread"),
              option(value := "read")("Mark as read"),
              option(value := "delete")("Delete")
            )
          )

        ),

        table(
          if (threads.nbResults > 0)
            tbody(cls := "infinitescroll")(
            if (threads.hasToPaginate)
              tr(
              th(cls := "pager none")(
                a(rel := "next", href := routes.Message.inbox(threads.nextPage | 1))("Next")
              )
            ),

            threads.currentPageResults.map { thread =>
              tr(cls := "paginated" +
                (if (thread.isUnReadBy(me)) " new" else "") +
                (if (thread.asMod) " mod" else ""))(
                td(cls := "author")(
                  userIdLink(thread.visibleOtherUserId(me), none)
                ),
                td(cls := "subject")(
                  a(href := routes.Message.thread(thread.id) + "#bottom")(thread.name)
                ),
                td(cls := "date")(
                  momentFromNow(thread.updatedAt)
                ),
                td(cls := "check")(
                  input(`type` := "checkbox", name := "threads", value := thread.id)
                )
              )
            }
          )

          else
            tbody(
              tr(
                td(cls := "no_messages")(
                  trans.noNewMessages()
                )
              )
            )
        )
      )
    }

}
