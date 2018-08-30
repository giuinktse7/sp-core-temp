package sp.dashboard

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import diode.react.ModelProxy

import scala.scalajs.js
import js.JSConverters._
import org.scalajs.dom.window
import sp.{SPWidgetBase, WidgetList}
import sp.circuit.{GlobalState, OpenWidget, SPGUICircuit, SetLayout}
import sp.dashboard.ReactGridLayout.LayoutElement


object Dashboard {
  case class Props(proxy: ModelProxy[List[OpenWidget]])
  case class State(width: Int)

  val cols = 12

  val currentlyDragging = SPGUICircuit.zoom(_.draggingState.dragging)


  class Backend($: BackendScope[Props, State]) {
    def render(props: Props, state: State) = {

      window.onresize = _ => $.modState(_.copy(width = window.innerWidth.toInt)).runNow()

      val prevOpenWidgets = props.proxy.value

      val widgets = for  {
        widgetData <- prevOpenWidgets
        widget <- WidgetList.list.find(_.name == widgetData.widgetType)
      } yield {
        <.div(
          DashboardItem(widget.render(SPWidgetBase(widgetData.id)),
            widgetData.widgetType,
            widgetData.id,
            widgetData.layout.h
          ),
          ^.key := widgetData.id.toString
        )
      }

      val bigLayout = for (openWidget <- prevOpenWidgets) yield {
        ReactGridLayout.LayoutElement(
          i = openWidget.id.toString,
          x = openWidget.layout.x,
          y = openWidget.layout.y,
          w = openWidget.layout.w,
          h = openWidget.layout.h
        )
      }

      val onLayoutChange: js.Array[LayoutElement] => Unit = layout => {
        val openWidgets = layout.map(e => e.key -> e).toMap

        val newLayout = for {
          prevWidget <- prevOpenWidgets
          widget <- openWidgets.get(prevWidget.id.toString)
        } yield {
            val layout = prevWidget.layout.copy(x = widget.x, y = widget.y, w = widget.w, h = widget.h)
            prevWidget.id -> layout
        }

        SPGUICircuit.dispatch(SetLayout(newLayout.toMap))
      }

      val gridLayout = ReactGridLayout(
        layout = bigLayout.map(x => x.asInstanceOf[LayoutElement]).toJSArray,
        width = state.width,
        cols = cols,
        draggableHandle = "." + DashboardCSS.widgetPanelHeader.htmlClass,
        onLayoutChange = onLayoutChange,
        children = widgets.toVdomArray
      )

      <.div(
        {if(!currentlyDragging.value) ^.className := "dropzonesDisabled" else EmptyVdom},
        gridLayout
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("Dashboard")
    .initialState(State(0))
    .renderBackend[Backend]
    .build

  def apply(proxy: ModelProxy[List[OpenWidget]]) = component(Props(proxy))
}