name := "rf-backend"

addCommandAlias("mg", "migrations/run")

scalaVersion := Version.scala

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
  shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
)

lazy val tileDependencies = Seq(
  Dependencies.geotrellisS3,
  Dependencies.geotrellisRaster,
  Dependencies.geotrellisVector,
  Dependencies.geotrellisSpark
)

lazy val root = Project("root", file("."))
  .settings(commonSettings:_*)

lazy val tile = Project("tile", file("tile"))
  .settings(commonSettings:_*)
  .settings(resolvers += "LocationTech GeoTrellis Releases" at "https://repo.locationtech.org/content/repositories/geotrellis-releases")
  .settings({ libraryDependencies ++= tileDependencies})

lazy val lambda = Project("lambda", file("lambda"))
  .settings(commonSettings:_*)
  .settings(externalPom())
