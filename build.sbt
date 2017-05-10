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
  "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
  "com.amazonaws" % "aws-lambda-java-events" % "1.3.0",
  "com.amazonaws" % "aws-lambda-java-log4j" % "1.0.0",
  Dependencies.geotrellisS3,
  Dependencies.geotrellisRaster,
  Dependencies.geotrellisVector,
  Dependencies.geotrellisSpark
)

val circeVersion = "0.7.0"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-optics"
).map(_ % circeVersion)

lazy val lambdaDependencies = Seq(
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
