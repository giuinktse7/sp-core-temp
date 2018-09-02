package sp

import org.scalajs.dom.document

object SwitchTheme {
  def to(theme: String) = {
    val themePath = s"/versionedAssets/stylesheets/themes/$theme.css"
    document.getElementById("bootstrap-theme").setAttribute("href", themePath)
  }
}
