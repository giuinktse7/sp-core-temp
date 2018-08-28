package sp

import org.scalajs.dom

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object ScalaJSExample {
  def main(args: Array[String]): Unit = {
    println("Scalajs running")
    WebSocketExample.WebSocketsApp().renderIntoDOM(dom.document.getElementById("react-app"))
  }
}
