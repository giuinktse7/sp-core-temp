package sp

import org.scalajs.dom.document
import sp.WidgetList.Widget
import sp.widget.service.ServiceListWidget

/**
  * Main class, initialized with scalaJSUseMainModuleInitializer := true
  */
object Main {
  def main(args: Array[String]): Unit = {
    println("hi")
    val widget = Widget("ServiceList", ServiceListWidget(), 3, 4)
    WidgetList.addWidgets(List(widget))

    Layout().renderIntoDOM(document.getElementById("spgui-root"))
  }
}
