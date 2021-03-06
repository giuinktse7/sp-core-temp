import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import play.sbt.PlayImport.{akkaHttpServer, filters, guice}
import sbt._

object Dependencies {
  object versions {
    val scala = "2.12.6"
    val akka = "2.5.14"
    val levelDbPort = "0.7"
    val levelDbJni = "1.8"
    val scalaTest = "3.0.5"
    val scalaJs = "0.9.1"
    val scalaJsReact = "1.2.3"
  }

  object npmVersions {
    val react = "16.2.0"
  }

  object clientDependencies {
    val scala = Def.setting(Seq(
      "org.scala-js" %%% "scalajs-dom" % versions.scalaJs,
      "com.github.japgolly.scalajs-react" %%% "core" % versions.scalaJsReact
    ))

    val javascript = Seq(
      "react" -> npmVersions.react,
      "react-dom" -> npmVersions.react
    )
  }

  val spDependencies = Def.setting(Seq(
    "com.typesafe.play" %%% "play-json" % "2.6.7",
    "org.julienrf" %%% "play-json-derived-codecs" % "4.0.1",
    PublishingSettings.orgNameFull %%% "sp-domain" % "0.9.12",
    PublishingSettings.orgNameFull %%% "sp-comm" % "0.9.11"
  ))

  val serverDependencies = Seq(
    guice,
    // akkaHttpServer,
    // "com.typesafe.akka" %% "akka-cluster" % versions.akka,
    // "com.typesafe.akka" %% "akka-cluster-tools" % versions.akka,
    // "com.typesafe.akka" %% "akka-persistence" % versions.akka,
    // "com.typesafe.akka" %% "akka-persistence-query" % versions.akka,
    "com.typesafe.akka" %% "akka-stream" % versions.akka

    // "org.iq80.leveldb" % "leveldb" % versions.levelDbPort,
    // "org.fusesource.leveldbjni" % "leveldbjni-all" % versions.levelDbJni,
  )

  val testDependencies = Seq(
    "org.scalactic" %% "scalactic" % versions.scalaTest,
    "org.scalatest" %% "scalatest" % versions.scalaTest % "test"
  )


}
