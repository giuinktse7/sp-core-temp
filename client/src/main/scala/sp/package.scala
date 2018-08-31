import japgolly.scalajs.react.vdom.TagMod
import scalacss.internal.StyleA
import japgolly.scalajs.react.vdom.html_<^._


package object sp {
  // This allows Scala.JS to choose dev settings during fastOptJS, and prod settings during fullOptJS.
  val CssSettings = scalacss.devOrProdDefaults
  implicit def toHtml(a: StyleA): TagMod = ^.className := a.htmlClass

  def bootstrap(classes: String*) = ^.className := classes.mkString(" ")
}