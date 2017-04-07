import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"
  lazy val geotrellisSparkEtl      = "org.locationtech.geotrellis" %% "geotrellis-spark-etl"              % Version.geotrellis
  lazy val geotrellisSpark         = "org.locationtech.geotrellis" %% "geotrellis-spark"                  % Version.geotrellis
  lazy val geotrellisS3            = "org.locationtech.geotrellis" %% "geotrellis-s3"                     % Version.geotrellis
  lazy val geotrellisRaster        = "org.locationtech.geotrellis" %% "geotrellis-raster"                 % Version.geotrellis
  lazy val geotrellisVector        = "org.locationtech.geotrellis" %% "geotrellis-vector"                 % Version.geotrellis
  val geotrellisUtil          = "org.locationtech.geotrellis" %% "geotrellis-util"                   % Version.geotrellis
}
