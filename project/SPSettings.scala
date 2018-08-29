
import sbt.Keys._
import sbt.{Resolver, _}
import xerial.sbt.Sonatype.autoImport._
import com.typesafe.sbt.SbtPgp.autoImport._
import Dependencies._

object SPSettings {


  lazy val compilerOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-language:higherKinds",
    "-Ypartial-unification"
  )

  lazy val repoResolvers: Seq[Resolver] = Seq(
    Resolver.sonatypeRepo("public"),
    Resolver.typesafeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )

  lazy val buildAndPublishSettings = Seq(
    organization := PublishingSettings.orgNameFull,
    homepage     := Some(PublishingSettings.githubSP()),
    licenses     := PublishingSettings.mitLicense,
    scalaVersion := versions.scala,
    scalacOptions ++= compilerOptions,
    resolvers ++= repoResolvers,
    useGpg := true,
    publishArtifact in Test := false,
    publishMavenStyle := true,
    publishTo := PublishingSettings.pubTo.value,
    pomIncludeRepository := { _ => false },
    sonatypeProfileName := PublishingSettings.groupIdSonatype,
    developers := List(Developer(id = "kristoferb", name = "kristofer Bengtsson", email = "kristofer.bengtsson@chalmers.se", url   = url("https://github.com/kristoferB")))
  )

}
