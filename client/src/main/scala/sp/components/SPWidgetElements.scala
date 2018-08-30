package sp.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.raw.SyntheticTouchEvent
import japgolly.scalajs.react.vdom.Attr
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.all.aria


object SPWidgetElements {
  def button(text: String, onClick: Callback): VdomNode =
    <.span(
      text,
      ^.onClick --> onClick,
      ^.className := "btn",
      ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
      ^.className := SPWidgetElementsCSS.clickable.htmlClass,
      ^.className := SPWidgetElementsCSS.button.htmlClass
    )

  def button(text:String, icon:VdomNode, onClick: Callback): VdomNode =
    <.span(
      <.span(text, ^.className:= SPWidgetElementsCSS.textIconClearance.htmlClass),
      icon,
      ^.onClick --> onClick,
      ^.className := "btn",
      ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
      ^.className := SPWidgetElementsCSS.clickable.htmlClass,
      ^.className := SPWidgetElementsCSS.button.htmlClass
    )

  def button(icon: VdomNode, onClick: Callback): VdomNode =
    <.span(icon,
      ^.onClick --> onClick,
      ^.className := "btn",
      ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
      ^.className := SPWidgetElementsCSS.clickable.htmlClass,
      ^.className := SPWidgetElementsCSS.button.htmlClass
    )

  def dropdown(text: String, contents: Seq[TagMod]): VdomElement =
    <.span(
      ^.className:= SPWidgetElementsCSS.dropdownRoot.htmlClass,
      <.span(
        ^.className:= SPWidgetElementsCSS.dropdownOuter.htmlClass,
        ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
        ^.className:= "dropdown",
        <.span(
          <.span(text, ^.className:= SPWidgetElementsCSS.textIconClearance.htmlClass),
          Icon.caretDown,
          VdomAttr("data-toggle") := "dropdown",
          ^.id:="something",
          ^.className := "nav-link dropdown-toggle",
          aria.hasPopup := "true",
          aria.expanded := "false",
          ^.className := "btn",
          ^.className := SPWidgetElementsCSS.button.htmlClass,
          ^.className := SPWidgetElementsCSS.clickable.htmlClass
        ),
        <.ul(
          contents.collect{
            case e => <.div(
              ^.className := SPWidgetElementsCSS.dropdownElement.htmlClass,
              e
            )
          }.toTagMod,
          ^.className := SPWidgetElementsCSS.dropDownList.htmlClass,
          ^.className := "dropdown-menu",
          aria.labelledBy := "something"
        )
      )
    )

  def dropdown(button: TagMod, contents: Seq[TagMod]): VdomElement =
    <.span(
      ^.className:= SPWidgetElementsCSS.dropdownRoot.htmlClass,
      <.span(
        ^.className:= SPWidgetElementsCSS.dropdownOuter.htmlClass,
        ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
        ^.className:= "dropdown",
        <.span(
          button,
          VdomAttr("data-toggle") := "dropdown",
          ^.id:="something",
          ^.className := "nav-link dropdown-toggle",
          aria.hasPopup := "true",
          aria.expanded := "false",
          ^.className := "btn",
          ^.className := SPWidgetElementsCSS.button.htmlClass,
          ^.className := SPWidgetElementsCSS.clickable.htmlClass
        ),
        <.ul(
          contents.collect{
            case e => <.div(
              ^.className := SPWidgetElementsCSS.dropdownElement.htmlClass,
              e
            )
          }.toTagMod,
          ^.className := SPWidgetElementsCSS.dropDownList.htmlClass,
          ^.className := "dropdown-menu",
          aria.labelledBy := "something"
        )
      )
    )

  def dropdownElement(text: String, icon: VdomNode, onClick: Callback): VdomNode =
    <.li(
      ^.className := SPWidgetElementsCSS.dropdownElement.htmlClass,
      <.span(icon, ^.className := SPWidgetElementsCSS.textIconClearance.htmlClass),
      text,
      ^.onClick --> onClick
    )

  def dropdownElement(text: String, onClick: Callback): VdomNode =
    <.li(
      ^.className := SPWidgetElementsCSS.dropdownElement.htmlClass,
      text,
      ^.onClick --> onClick
    )

  def buttonGroup(contents: Seq[TagMod]): VdomElement =
    <.div(
      ^.className:= "form-inline",
      contents.toTagMod
    )

  object TextBox {
    case class Props( defaultText: String, onChange: String => Callback )
    case class State( text: String )

    class Backend($: BackendScope[Props, State]) {
      def render(p:Props,s: State) =
        <.span(
          ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
          ^.className := "input-group",
          <.input(
            ^.className := SPWidgetElementsCSS.textBox.htmlClass,
            ^.className := "form-control",
            ^.placeholder := p.defaultText,
            ^.aria.describedBy := "basic-addon1",
            ^.onChange ==> onFilterTextChange(p)
          )
        )
      def onFilterTextChange(p:Props)(e: ReactEventFromInput): Callback =
        e.extract(_.target.value)(p.onChange) // TODO check if this works
    }

    private val component = ScalaComponent.builder[Props]("SPTextBox")
      .initialState(State("test"))
      .renderBackend[Backend]
      .build

    def apply(defaultText: String, onChange: String => Callback) =
      component(Props(defaultText, onChange))
  }

  import java.util.UUID
  import sp.dragging._

  object DragoverZoneRect {
    case class Props(id: UUID, cb: DragDropData => Unit, dropData: DropData, x: Float, y: Float, w: Float, h: Float)
    case class State(hovering: Boolean = false, id: UUID = UUID.randomUUID())
    
    class Backend($: BackendScope[Props, State]) {
      def setHovering(hovering: Boolean): Unit =
        $.modState(s => s.copy(hovering = hovering)).runNow()

      def render(p:Props, s:State) = {
        <.span(
          <.span(
            ^.id := p.id.toString,
            ^.left := p.x.toString,
            ^.top := p.y.toString,
            ^.width := p.w.toString,
            ^.height := p.h.toString,
            ^.className := SPWidgetElementsCSS.dropZone.htmlClass,
            (^.className := SPWidgetElementsCSS.blue.htmlClass).when(s.hovering),
            ^.onMouseOver --> Callback { Dragging.setDraggingTarget(p.id) }
          )
        )
      }

      def didUpdate(prevProps: Props, currentProps: Props) = Callback {
        Dragging.unsubscribeToDropEvents(prevProps.id)
        Dragging.subscribeToDropEvents(currentProps.id, currentProps.cb, currentProps.dropData)
        Dragging.dropzoneResubscribe(currentProps.id, prevProps.id)
      }

      def didMount(props: Props) = Callback {
        Dragging.dropzoneSubscribe(props.id, setHovering)
        Dragging.subscribeToDropEvents(props.id, props.cb, props.dropData)
      }

      def willUnmount(props: Props) = Callback {
        Dragging.dropzoneUnsubscribe(props.id)
        Dragging.unsubscribeToDropEvents(props.id)
      }
    }

    private val component = ScalaComponent.builder[Props]("SPDragZone")
      .initialState(State())
      .renderBackend[Backend]
      .componentDidUpdate(c => c.backend.didUpdate(c.prevProps, c.currentProps))
      .componentDidMount(c => c.backend.didMount(c.props))
      .componentWillUnmount(c => c.backend.willUnmount(c.props))
      .build

    def apply(cb: DragDropData => Unit, dropData: DropData, x: Float, y: Float, w: Float, h: Float) =
      component(Props(UUID.randomUUID(), cb, dropData, x, y, w, h))
  }

  object DragoverZoneWithChild {
    case class Props(id: UUID, cb: DragDropData => Unit, dropData: DropData, subComponent: VdomNode)
    case class State(hovering: Boolean = false, id: UUID = UUID.randomUUID())
    
    class Backend($: BackendScope[Props, State]) {
      def setHovering(hovering: Boolean) =
        $.modState(s => s.copy(hovering = hovering)).runNow()

      def render(p:Props, s:State) = {
        <.span(
          ^.className := SPWidgetElementsCSS.dropZoneUnzisedOuter.htmlClass,
          <.span(
            ^.id := p.id.toString,
            ^.className := SPWidgetElementsCSS.dropZone.htmlClass,
            ^.className := SPWidgetElementsCSS.fillParent.htmlClass,
            {if(s.hovering)
              ^.className := SPWidgetElementsCSS.blue.htmlClass
            else ""},
            ^.onMouseOver --> Callback({
              Dragging.setDraggingTarget(p.id)
            })
          ),
          p.subComponent
        )
      }

      def didUpdate(prevProps: Props, currentProps: Props) = Callback {
        Dragging.unsubscribeToDropEvents(prevProps.id)
        Dragging.subscribeToDropEvents(currentProps.id, currentProps.cb, currentProps.dropData)
        Dragging.dropzoneResubscribe(currentProps.id, prevProps.id)
      }

      def didMount(props: Props) = Callback {
        Dragging.dropzoneSubscribe(props.id, setHovering)
        Dragging.subscribeToDropEvents(props.id, props.cb, props.dropData)
      }

      def willUnmount(props: Props) = Callback {
        Dragging.dropzoneUnsubscribe(props.id)
        Dragging.unsubscribeToDropEvents(props.id)
      }
    }

    private val component = ScalaComponent.builder[Props]("DragoverZoneWithChild")
      .initialState(State())
      .renderBackend[Backend]
      .componentDidUpdate(c => c.backend.didUpdate(c.prevProps, c.currentProps))
      .componentDidMount(c => c.backend.didMount(c.props))
      .componentWillUnmount(c => c.backend.willUnmount(c.props))
      .build
    
    def apply(cb: DragDropData => Unit, dropData: DropData, subComponent: VdomNode) =
      component(Props(UUID.randomUUID(), cb, dropData, subComponent))
  }

  private def onTouch(e: ReactTouchEvent) = Callback {
    val Point(x, y) = touchPosition(e)
    Dragging.onDragMove(x, y)
  }

  def draggable(label: String, data: DragData, typeName: String, onDrop: DragDropData => Unit): TagMod = {
    Seq(
      ^.onTouchStart ==> handleTouchDragStart(label, data, onDrop),
      ^.onTouchMoveCapture ==> onTouch,
      ^.onTouchEnd ==> { _ => Callback (Dragging.onDragStop()) },
      ^.onMouseDown ==> handleDragStart(label, data, onDrop)
    ).toTagMod
  }

  /*
   This is used to generate mouse events when dragging on a touch screen, which will trigger
   the ^.onMouseOver on any element targeted by the touch event. Mobile browsers do not support
   mouse-hover related events (and why should they) so this is a way to deal with that.
   */
  def handleTouchDrag(e: ReactTouchEvent) = Callback {
    val Point(x, y) = touchPosition(e)
    sp.dragging.Dragging.onDragMove(x, y)
  }

  case class Point[N: Numeric](x: N, y: N)

  /**
    * Returns the page position of a touch event.
    */
  private def touchPosition(e: ReactTouchEvent) = Point(e.touches.item(0).pageX.toFloat, e.touches.item(0).pageY.toFloat)

  /**
    * Returns the page position of a mouse event.
    */
  private def mousePosition(e: ReactMouseEvent) = Point(e.pageX.toFloat, e.pageY.toFloat)

  def handleTouchDragStart(label: String, data: DragData, onDrop: DragDropData => Unit)(e: ReactTouchEvent): Callback = {
    val Point(x, y) = touchPosition(e)
    Callback(Dragging.onDragStart(label, data, x, y, onDrop))
  }

  def handleDragStart(label: String, data: DragData, onDrop: DragDropData => Unit)(e: ReactMouseEvent): Callback = {
    val Point(x, y) = mousePosition(e)
    Callback(Dragging.onDragStart(label, data, x, y, onDrop))
  }
}
