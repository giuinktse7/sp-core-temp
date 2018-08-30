package sp.dragging

import scalacss.DevDefaults._
import sp.theming.SPStyleSheet

object DraggingCSS extends SPStyleSheet {
  import dsl._

  val dragElement = style(
    position.absolute,
    userSelect:= "none",
    pointerEvents:= "none"
  )

  val overlay = style(
    unsafeRoot("body")(
      userSelect := "none"
    ),
    height(100.%%),
    width(100.%%),
    position.absolute,
    top(0.px)
  )

  val hidden = style(display.none)

  this.addToDocument()
}
