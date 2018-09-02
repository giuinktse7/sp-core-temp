package sp.menu

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import sp.components.{Icon, SPNavbarElements}
import sp.circuit._
import diode.react.ModelProxy
import sp.{SwitchTheme, bootstrap, toHtml}
import sp.theming.SPStyleSheet
import sp.theming.SPStyleSheet.Theme

object SPMenu {
  import sp.menu.{SPMenuCSS => css}
  case class Props(proxy: ModelProxy[Settings], additionalNavElements: Seq[VdomElement])

  class SPMenuBackend($: BackendScope[Props, Unit]){
    def menuItems(props: Props) = {
      <.div(
        bootstrap("collapse", "navbar-collapse", "align-self-stretch"),
        ^.id := "navbar-contents",
        <.ul(
          bootstrap("navbar-nav", "mr-auto", "align-self-stretch"),
          WidgetMenu(),
          <.button(
            bootstrap("btn", "btn-primary"),
            ^.onClick --> Callback(SwitchTheme.to("dark")),
            "Primary"),
          renderOptions(props),
          props.additionalNavElements.toTagMod(x => x.apply()), // Insert any additional menu items added by someone else
          SPNavbarElements.button("Close all", Callback(SPGUICircuit.dispatch(CloseAllWidgets)))
        )
      )
    }

    def renderOptions(props: Props) = {
      val toggleHeaders = SPNavbarElements.dropdownElement(
        "Toggle headers",
        if(props.proxy().showHeaders) Icon.toggleOn else Icon.toggleOff,
        onClick = Callback(SPGUICircuit.dispatch(ToggleHeaders))
      )

      def selectTheme(theme: Theme) = Callback {
        SPGUICircuit.dispatch(
          SetTheme(SPStyleSheet.themeList.find(_.name == theme.name).get)
        )
        org.scalajs.dom.window.location.reload() // reload the page
      }

      def renderThemeOption(theme: Theme) = {
        val selected = props.proxy().theme.name == theme.name
        SPNavbarElements.dropdownElement(
          theme.name,
          if (selected) Icon.checkSquare else Icon.square,
          onClick = selectTheme(theme)
        )
      }

      SPNavbarElements.dropdown("Options", toggleHeaders +: SPStyleSheet.themeList.map(renderThemeOption))
    }

    def render(props: Props): VdomElement = {
      <.nav(
        bootstrap("navbar", "navbar-light", "navbar-expand-lg", "bg-light", "p-0"),
        <.a(bootstrap("navbar-brand", "p-0", "m-0"), <.div(css.spLogo)),
        <.button(
          bootstrap("navbar-toggler"),
          VdomAttr("data-toggle") := "collapse",
          VdomAttr("data-target") := "#navbar-contents",
          <.span(bootstrap("navbar-toggler-icon"))
        ),
        menuItems(props)
      )
    } 
  }

  private val component = ScalaComponent.builder[Props]("SPMenu")
    .renderBackend[SPMenuBackend]
    .build

  def apply(proxy: ModelProxy[Settings]) = component(Props(proxy, extraNavbarElements))

  private var extraNavbarElements: Seq[VdomElement] = Seq()

  /**
    * Used to add new navigataion elements in the menu bar.
    * @param xs
    */
  def addNavElem(xs: Seq[VdomElement]): Unit = extraNavbarElements ++= xs

  def addNavElem(x: VdomElement): Unit = addNavElem(Seq(x))

}