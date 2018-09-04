import sbtcrossproject.CrossPlugin.autoImport.crossProject
import sbtcrossproject.CrossType
import Dependencies._


lazy val publishVersion = "testingLocal180829"
lazy val publishName = "sp-core"

def npmAssetsIn(paths: Seq[Seq[String]])  = NpmAssets.ofProject(client) { module =>
  paths.map { path => path.foldLeft(module)(_ / _).allPaths }
    .reduce(_ +++ _)
}

/**
  * These are files from npm-dependencies that we want to be able to serve through versionedAssets or assets.
  */
lazy val npmAssetLocations = Seq(
  Seq("react-grid-layout", "css"),
  Seq("bootstrap", "dist"),
  Seq("@fortawesome", "fontawesome-free", "css"),
  Seq("@fortawesome", "fontawesome-free", "webfonts"),
  Seq("jquery", "dist")
)

lazy val server = project.in(file("server"))
  .settings(commonSettings)
  .settings(SPSettings.buildAndPublishSettings)
  .settings(
    name := publishName + "-server",
    libraryDependencies ++= serverDependencies,
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    npmAssets ++= npmAssetsIn(npmAssetLocations).value
  )
  .enablePlugins(PlayService, PlayLayoutPlugin, WebScalaJSBundlerPlugin)
  //.disablePlugins(PlayLogback)
  .dependsOn(sharedJvm)

lazy val client = project.in(file("client"))
  .settings(commonSettings)
  .settings(SPSettings.buildAndPublishSettings)
  .settings(
    name := publishName + "-client",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= clientDependencies.scala.value,
    npmDependencies in Compile ++= clientDependencies.javascript
  ).enablePlugins(ScalaJSBundlerPlugin, ScalaJSWeb)
  .dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  version := publishVersion,
  libraryDependencies ++= spDependencies.value,
  webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly(),
  webpackBundlingMode in fullOptJS := BundlingMode.Application
)

// loads the server project at sbt startup
// onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}
