package sp.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import sp.{bootstrap, toHtml}

object SPNavbarElements {
  import sp.components.{SPNavbarElementsCSS => css}

  def button(text:String, onClick: Callback): VdomNode =
    <.li(
      bootstrap("d-flex", "align-items-stretch"),
      <.a(
        bootstrap("d-flex", "align-items-center"),
        ^.href := "#",
        css.leftRightPad,
        ^.onClick --> onClick,
        text
      )
    )

  def button(text:String, icon:VdomNode, onClick: Callback): VdomNode =
    <.li(
      <.a(
        <.span(text, SPWidgetElementsCSS.textIconClearance),
        icon,
        ^.onClick --> onClick,
        css.clickable,
        css.button
      )
    )

  def button(icon:VdomNode, onClick: Callback): VdomNode =
    <.li(
      <.a(icon,
        ^.onClick --> onClick,
        css.clickable,
        css.button
      )
    )

  def dropdown(text: String, contents: Seq[TagMod]): VdomElement = {
    <.li(
      bootstrap("nav-item", "dropdown", "d-flex align-items-stretch"),
      <.a(
        ^.href := "#",
        bootstrap("nav-link", "dropdown-toggle", "d-flex align-items-center"),
        ^.id := "something",
        VdomAttr("data-toggle") := "dropdown",
        text
      ),
      <.ul(
        bootstrap("dropdown-menu"),
        contents.toTagMod
      )
    )
  }

  def dropdownElement(text: String, icon: VdomNode, onClick: Callback): VdomNode = 
    <.li(
      bootstrap("dropdown-item"),
      css.dropdownElement,
      <.span(icon, css.textIconClearance),
      ^.onClick --> onClick,
      text
    )

  def dropdownElement(text: String, onClick: Callback): VdomNode =
    <.li(bootstrap("dropdown-item"), css.dropdownElement, ^.onClick --> onClick, text)

  def dropdownElement(content: TagMod, onClick: Callback): VdomNode =
    <.li(bootstrap("dropdown-item"), css.dropdownElement, ^.onClick --> onClick, content)

  def dropdownElement(content: TagMod): VdomNode =
    <.li(bootstrap("dropdown-item"), css.dropdownElement, content)

  object TextBox {
    case class Props(contentText: String, placeholderText: String, onChange: String => Callback )
    case class State(text: String = "")

    class Backend($: BackendScope[Props, State]) {
      def render(p:Props, s: State) =
        <.span(
          ^.className := "input-group",
          <.input(
            ^.className := "form-control",
            ^.placeholder := p.placeholderText,
            ^.value := p.contentText,
            ^.aria.describedBy := "basic-addon1",
            ^.onChange ==> onFilterTextChange(p)
          )
        )
      def onFilterTextChange(p:Props)(e: ReactEventFromInput): Callback =
        e.extract(_.target.value)(v => p.onChange(v))
    }

    private val component = ScalaComponent.builder[Props]("SPTextBox")
      .initialState(State())
      .renderBackend[Backend]
      .build

    // atm broken; gives funny behaviour where you can just enter one letter that you cannot see
    //def apply(placeholderText: String, onChange: String => Callback): VdomElement =
    //  component(Props("", placeholderText, onChange))

    def apply(contentText: String, placeholderText: String, onChange: String => Callback): VdomElement =
      component(Props(contentText, placeholderText, onChange))
  }
}


