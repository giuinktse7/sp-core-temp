package sp.dashboard

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import scalacss.ScalaCssReact._

import sp.circuit.{ SPGUICircuit, CloseWidget, CollapseWidgetToggle }
import sp.components.Icon

object DashboardItem {
  import sp.dashboard.{DashboardCSS => css}
  case class Props(
    element: VdomElement,
    widgetType: String,
    id: java.util.UUID,
    panelHeight: Int
  )


  val showHeaders = SPGUICircuit.zoom(_.settings.showHeaders)

  class DashboardItemBackend($: BackendScope[Props, Unit]) {

    def render(props: Props) = {
      println("Rendering DashboardItem")
      val closeButton = <.a(
        ^.className := "close",
        ^.onClick --> Callback(SPGUICircuit.dispatch(CloseWidget(props.id))),
        Icon.close,
        css.widgetPanelButton
      )

      val toggle = <.a(
        VdomAttr("data-toggle") := "tooltip",
        VdomAttr("title") := "toggle panel",
        ^.className := "close",
        ^.onClick --> Callback(SPGUICircuit.dispatch(
          CollapseWidgetToggle(props.id)
        )),
        css.widgetPanelButton,
        if(props.panelHeight == 1) Icon.arrowDown else Icon.arrowUp
      )

      <.div(
        css.widgetPanel,
        <.div(
          ^.className := "modal-header",
          css.widgetPanelHeader,
          <.h5(css.widgetPanelLabel, props.widgetType),
          <.div(
            closeButton,
            toggle
          ),
          css.widgetPanelHidden.unless(showHeaders.value)
        ),
        <.div(
          css.widgetPanelBody,
          <.div(
            ^.className := "panel-body",
            css.widgetPanelContent,
            props.element
          )
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("DashboardItem")
    .renderBackend[DashboardItemBackend]
    .build

  def apply(element: VdomElement, widgetType: String,id: java.util.UUID, panelHeight: Int) =
    component(Props(element, widgetType, id, panelHeight))

}
