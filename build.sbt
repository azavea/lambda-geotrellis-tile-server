import sbt.Keys._
import sbt._
import sbtrelease.{Version => SbtVersion}

name := "lambda-geotrellis-tile-server"

resolvers += Resolver.sonatypeRepo("public")
scalaVersion := "2.11.8"
releaseNextVersion := { ver => SbtVersion(ver).map(_.bumpMinor.string).getOrElse("Error") }

lazy val commonSettings = Seq(
  organization := "com.jisantuc",
  version := "0.0.1",
  cancelable in Global := true,
  scalaVersion := Version.scala,
  scalacOptions := Seq(
    "-deprecation",
    "-unchecked",
    "-feature",
    "-language:implicitConversions",
    "-language:reflectiveCalls",
    "-language:higherKinds",
    "-language:postfixOps",
    "-language:existentials",
    "-language:experimental.macros",
    "-feature"
  ),
  shellPrompt := { s => Project.extract(s).currentProject.id + " > " },
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)


libraryDependencies ++= Seq(
  Dependencies.awsJavaCore,
  Dependencies.awsJavaEvents,
  Dependencies.awsJavaLog4j,
  Dependencies.commonsIo,
  Dependencies.geotrellisS3,
  Dependencies.geotrellisRaster,
  Dependencies.geotrellisSpark,
  Dependencies.circeCore,
  Dependencies.circeGeneric,
  Dependencies.circeParser
)

lazy val root = Project("root", file("."))
  .settings(resolvers += "LocationTech GeoTrellis Releases" at "https://repo.locationtech.org/content/repositories/geotrellis-releases")
  .settings(commonSettings:_*)

assemblyMergeStrategy in assembly := {
  case "reference.conf" => MergeStrategy.concat
  case "application.conf" => MergeStrategy.concat
  case n if n.endsWith(".SF") || n.endsWith(".RSA") || n.endsWith(".DSA") => MergeStrategy.discard
  case "META-INF/MANIFEST.MF" => MergeStrategy.discard
  case _ => MergeStrategy.first
}
assemblyJarName in assembly := s"lambda-geotrellis-tile-server.jar"

import S3._
s3Settings
mappings in upload := Seq((file(s"target/scala-2.11/${name.value}.jar"), s"${name.value}.jar"))
host in upload := "lambda-geotrellis-tile-server-jar.s3.amazonaws.com"
progress in upload := true
upload <<= upload dependsOn assembly

initialCommands in console := """
  |import io.circe.parser._
  |import io.circe.syntax._
  |import geotrellis.spark.io._
  |import geotrellis.spark.io.s3._
""".trim.stripMargin
