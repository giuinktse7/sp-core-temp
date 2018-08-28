package sp

import sp.shared.SharedMessages
import org.scalajs.dom
import org.scalajs.dom.document

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object ScalaJSExample {

  val Hello =
  ScalaComponent.builder[String]("Hello")
    .render_P(name => <.div("Hello there ", name))
    .build

  def main(args: Array[String]): Unit = {
    dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
    Hello("Dude!").renderIntoDOM(document.body)
  }
}
