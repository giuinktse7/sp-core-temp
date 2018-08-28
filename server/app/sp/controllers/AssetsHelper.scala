package sp.controllers

import controllers.Assets.Asset
import play.twirl.api.Html
import play.api.mvc.Call

object AssetsHelper {
  private def scriptTag(src: String, attrs: String = "") = {
    s"""<script src=$src type="text/javascript" $attrs></script>"""
  }
  def scalaJsScripts(projectName: String, getAsset: String => String, resourceExists: String => Boolean) = {
    val dependencies = projectDependencies(projectName, resourceExists, getAsset)
    val selResult = projectBundle(projectName, resourceExists, getAsset)

    Seq(dependencies, selResult).mkString("\n")
  }

  private def projectDependencies(projectName: String, resourceExists: String => Boolean, getAsset: String => String) = {
    Seq(".min.js", ".js")
      .map(s"${projectName.toLowerCase}-jsdeps" + _)
      .find(resourceExists)
      .map(name => scriptTag(getAsset(name)))
      .getOrElse("<!-- Could not find jsdeps -->")
  }

  private def projectBundle(projectName: String, resourceExists: String => Boolean, getAsset: String => String) = {
    Seq("-opt.js", "-fastopt.js", "-opt-bundle.js", "-fastopt-bundle.js")
      .map(projectName.toLowerCase + _)
      .find(resourceExists)
      .map(name => scriptTag(getAsset(name)))
      .getOrElse("<!-- Could not find bundle -->")
  }

  def index(title: String, versioned: String => Call) = {
    Html(s"""
    <!DOCTYPE html>
      <html>
        <head>
          <title>$title</title>
          <link rel="stylesheet" media="screen" href="${versioned("stylesheets/main.css")}">
          <link rel="shortcut icon" type="image/png" href="${versioned("images/favicon.png")}">
        </head>
        <body>
        <h2>Play and Scala.js share a same message</h2>
        <ul>
          <li>From Scala.js: <em id="scalajsShoutOut"></em></li>
        </ul>
          ${AssetsHelper.scalaJsScripts("client", versioned(_).toString, name => getClass.getResource(s"/public/$name") != null)}
        </body>
      </html>
    """)
  }
}