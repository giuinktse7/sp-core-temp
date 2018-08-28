import sbt.Keys._
import sbt._
// import xerial.sbt.Sonatype.autoImport._
import Dependencies._

object SPSettings {
  /*

  lazy val compilerOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-language:higherKinds",
    "-Ypartial-unification"
  )

  lazy val jsDependencies = Def.setting(Seq(

  ))


*/

  /*
  lazy val repoResolvers: Seq[Resolver] = Seq(
    Resolver.sonatypeRepo("public"),
    Resolver.typesafeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
  */

  lazy val sharedSettings = Seq(
    name := PublishingSettings.projectName,
    description := PublishingSettings.description,
    version := PublishingSettings.version,
    // resolvers ++= repoResolvers,
    scalaVersion := versions.scala,
    organization := PublishingSettings.orgNameFull,
    libraryDependencies ++= spDependencies.value
    /*
    scmInfo := Some(ScmInfo(
      PublishingSettings.githubSP(PublishingSettings.projectName),
      PublishingSettings.githubscm(PublishingSettings.projectName)
    ))*/
  )

/*
  lazy val defaultBuildSettings = Seq(
    name := PublishingSettings.projectName,
    description  := PublishingSettings.description,
    version := PublishingSettings.version,
    scalaVersion := versions.scala,
    scalacOptions ++= compilerOptions,
    resolvers ++= repoResolvers,
    useGpg := true,
    publishArtifact in Test := false,
    publishMavenStyle := true,
    publishTo := PublishingSettings.pubTo.value,
    pomIncludeRepository := { _ => false },
    sonatypeProfileName := PublishingSettings.groupIdSonatype,
    developers := ProjectSettings.developers,
    organization := PublishingSettings.orgNameFull,
    homepage     := Some(PublishingSettings.githubSP()),
    licenses     := PublishingSettings.mitLicense,
    scmInfo := Some(ScmInfo(
      PublishingSettings.githubSP(PublishingSettings.projectName),
      PublishingSettings.githubscm(PublishingSettings.projectName)
    ))
  )
  */
}
