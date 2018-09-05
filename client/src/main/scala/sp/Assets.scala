package sp

import org.scalajs.dom.document

object Assets {
  /**
    * TODO: Find better solution to this, ideally a config file for the frontend part.
    */
  var devMode = true

  def setTheme(theme: String): Unit = {
    val link = document.getElementById("sp-theme")
    val newHref = link
      .getAttribute("href")
      .split("/")
      .dropRight(1)
      .mkString("/") + s"/$theme.css"

    link.setAttribute("href", newHref)
  }

  def getLibAsset(asset: String) = {
    val prefix = if (!devMode) "versionedAssets/lib/sp-core-server/" else "versionedAssets/"
    prefix + asset
  }
}
