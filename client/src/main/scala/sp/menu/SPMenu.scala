package sp.menu

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import sp.components.{Icon, SPNavbarElements}
import sp.circuit._
import diode.react.ModelProxy
import sp.theming.SPStyleSheet
import sp.bootstrap
import sp.toHtml

object SPMenu {
  import sp.menu.{SPMenuCSS => css}
  case class Props(proxy: ModelProxy[Settings], extraNavElem: Seq[VdomElement])

  class SPMenuBackend($: BackendScope[Props, Unit]){
    def menuItems() = {
      // <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <.div(bootstrap("collapse", "navbar-collapse"),
          ^.id := "navbar-contents"
        )
    }
    def render(p: Props): VdomElement = {
      <.nav(bootstrap("navbar", "navbar-light", "navbar-expand-lg", "bg-light"),
        <.a(bootstrap("navbar-brand"),
          css.splogoContainer,
          <.div(css.spLogo)
        ),
        <.button(bootstrap("navbar-toggler"),
          VdomAttr("data-toggle") := "collapse",
          VdomAttr("data-target") := "#navbar-contents",
          <.span(bootstrap("navbar-toggler-icon"))
        ),
        menuItems()
      )
      /*
      <.nav(
        css.topNav.htmlClass,
        bootstrap("navbar", "navbar-default", "navbar-expand-lg"),
        // navbar header: logo+toggle button
        <.div(
          bootstrap("navbar-header"),
          css.navbarContents.htmlClass,
          <.a(
            css.navbarToggleButton.htmlClass,
            bootstrap("navbar-toggle", "collapsed"),
            VdomAttr("data-toggle") := "collapse",
            VdomAttr("data-target") := "#navbar-contents",
            <.div(
              css.navbarToggleButtonIcon,
              Icon.bars
            )
          ),
          <.a(
            bootstrap("navbar-brand"),
            css.splogoContainer.htmlClass,
            <.div(css.spLogo.htmlClass)
          )
        ),

        // navbar contents
        <.div(
          css.navbarContents,
          ^.className := "collapse navbar-collapse",
          ^.id := "navbar-contents",
          <.ul(
            ^.className := "nav navbar-nav",

            WidgetMenu(),
            SPNavbarElements.dropdown(
              "Options",
              Seq(
                SPNavbarElements.dropdownElement(
                  "Toggle headers",
                  {if(p.proxy().showHeaders) Icon.toggleOn else Icon.toggleOff},
                  Callback(SPGUICircuit.dispatch(ToggleHeaders))
                ),
                SPStyleSheet.themeList.map(theme =>
                  SPNavbarElements.dropdownElement(
                    theme.name,
                    {if(p.proxy().theme.name == theme.name) Icon.checkSquare else Icon.square},
                    Callback({
                      SPGUICircuit.dispatch(
                        SetTheme(
                          SPStyleSheet.themeList.find(e => e.name == theme.name).get
                        )
                      )
                      org.scalajs.dom.window.location.reload() // reload the page
                    })
                  )
                ).toTagMod
              )
            ),
            p.extraNavElem.toTagMod(x => x.apply()), // Insert any additional menu items added by someone else
            SPNavbarElements.button("Close all", Callback(SPGUICircuit.dispatch(CloseAllWidgets)))
          )
        )
      )
      */
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