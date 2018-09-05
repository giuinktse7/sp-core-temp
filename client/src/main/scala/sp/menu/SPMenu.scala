package sp.menu

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import sp.components.{Icon, SPNavbarElements}
import sp.circuit._
import diode.react.ModelProxy
import sp.{Assets, bootstrap, toHtml}
import sp.theming.SPStyleSheet
import sp.theming.SPStyleSheet.Theme

object SPMenu {
  case class Props(proxy: ModelProxy[Settings], additionalNavElements: Seq[VdomElement])

  class SPMenuBackend($: BackendScope[Props, Unit]){
    def menuItems(props: Props) = {
      <.div(
        bootstrap("collapse", "navbar-collapse"),
        ^.id := "navbar-contents",
        <.ul(
          bootstrap("navbar-nav", "mr-auto"),
          WidgetMenu(),
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
        bootstrap("navbar", "navbar-expand-lg", "navbar-bg", "p-0"),
        //<.a(bootstrap("navbar-brand", "p-0", "m-0"), <.div(css.spLogo)),
        <.a(
          bootstrap("navbar-brand", "p-0", "m-0"),
          <.img(^.src := Assets.getLibAsset("images/splogo_title.svg"), ^.height := 50.px)
        ),
        <.button(
          bootstrap("navbar-toggler"),
          VdomAttr("data-toggle") := "collapse",
          VdomAttr("data-target") := "#navbar-contents",
          <.span(bootstrap("navbar-toggler-icon"), ^.className := "fas fa-bars")
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