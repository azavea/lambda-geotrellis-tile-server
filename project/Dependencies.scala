import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"
  lazy val geotrellisSparkEtl      = "org.locationtech.geotrellis" %% "geotrellis-spark-etl"              % Version.geotrellis
  lazy val geotrellisSpark         = "org.locationtech.geotrellis" %% "geotrellis-spark"                  % Version.geotrellis
  lazy val geotrellisS3            = "org.locationtech.geotrellis" %% "geotrellis-s3"                     % Version.geotrellis
  lazy val geotrellisRaster        = "org.locationtech.geotrellis" %% "geotrellis-raster"                 % Version.geotrellis
  lazy val geotrellisVector        = "org.locationtech.geotrellis" %% "geotrellis-vector"                 % Version.geotrellis
  val geotrellisUtil          = "org.locationtech.geotrellis" %% "geotrellis-util"                   % Version.geotrellis
  val awsJavaCore = "com.amazonaws" % "aws-lambda-java-core" % Version.awsJavaCore % "provided"
  val awsJavaEvents = "com.amazonaws" % "aws-lambda-java-events" % Version.awsJavaEvents % "provided"
  val awsJavaLog4j = "com.amazonaws" % "aws-lambda-java-log4j" % Version.awsJavaLog4j % "provided"
  val commonsIo = "commons-io" % "commons-io" % Version.commonsIo
  val circeCore = "io.circe" %% "circe-core" % Version.circe
  val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
  val circeParser = "io.circe" %% "circe-parser" % Version.circe
}
