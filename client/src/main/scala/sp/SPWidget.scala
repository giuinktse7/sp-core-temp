package sp

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scala.util.Try
import sp.domain._
import java.util.UUID

import sp.circuit._


case class SPWidgetBase(id: UUID) {

  def updateWidgetData(data: SPValue): Unit = {
    SPGUICircuit.dispatch(UpdateWidgetData(id, data))
  }

  def getWidgetData = {
    SPGUICircuit.zoom(_.widgetData.xs.get(id)).value.getOrElse(SPValue.empty)
  }

  def updateGlobalState(state: GlobalState): Unit = {
    SPGUICircuit.dispatch(UpdateGlobalState(state))
  }

  def openNewWidget(widgetType: String, initialData: SPValue = SPValue.empty) = {
    val w = AddWidget(widgetType = widgetType)
    val d = UpdateWidgetData(w.id, initialData)

    SPGUICircuit.dispatch(d)
    SPGUICircuit.dispatch(w)
  }

  def closeSelf() = SPGUICircuit.dispatch(CloseWidget(id))

}

object SPWidget {
  case class Props(spwb: SPWidgetBase, renderWidget: SPWidgetBase => VdomElement)
  private val component = ScalaComponent.builder[Props]("SpWidgetComp")
    .render_P(p => p.renderWidget(p.spwb))
    .build

  def apply(renderWidget: SPWidgetBase => VdomElement): SPWidgetBase => VdomElement =
    spwb => component(Props(spwb, renderWidget))
}