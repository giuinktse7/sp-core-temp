package sp

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import sp.circuit.{SPGUICircuit, SPGUIModel}
import sp.menu.SPMenu
import sp.dashboard.Dashboard
import sp.dragging.Dragging
import sp.modal.Modal

object Layout {
  def connect[A <: AnyRef, B <: VdomElement](f: SPGUIModel => A) = SPGUICircuit.connect(f)

  val component = ScalaComponent.builder[Unit]("Layout")
    .render(_ =>
      <.div(
        ^.className := GlobalCSS.layout.htmlClass,
        connect(_.modalState)(Modal(_)),
        connect(_.settings)(SPMenu(_)),
        connect(s => s.openWidgets.xs.values.toList)(Dashboard(_)),
        Dragging.mouseMoveCapture,
        connect(_.draggingState)(Dragging(_))
      )
    )
    .build

  def apply() = component()
}