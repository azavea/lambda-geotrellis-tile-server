package io.github.jisantuc.gtlambda.tile

import geotrellis.raster.{IntArrayTile, MultibandTile, Tile}
import geotrellis.raster.render.{ColorRamps, ColorMap}
import geotrellis.spark.{LayerId, SpatialKey}
import geotrellis.spark.io.ValueNotFoundError
import geotrellis.spark.io.s3.{S3ValueReader, S3AttributeStore}

import io.circe.generic.JsonCodec

import spray.json._
import DefaultJsonProtocol._

@JsonCodec
case class RgbRequest (
  x: Int,
  y: Int,
  z: Int,
  s: String
) extends Request {
  implicit val spatialKeyFormat = jsonFormat2(SpatialKey.apply _)

  val source = s match {
    case "nlcd" => "nlcd-2011-canopy-tms-epsg3857"
    case "ned" => "us-ned-tms-epsg3857"
    case _ => throw new RuntimeException(s"Unknown layer: $s")
  }

  def toTile: Tile = {
    val store = S3AttributeStore("azavea-datahub", "catalog")
    val layerId = new LayerId(source, z)
    val reader = new S3ValueReader(store).reader[SpatialKey, Tile](layerId)
    try {
      reader.read(SpatialKey(x, y))
    } catch {
      case e: ValueNotFoundError => IntArrayTile.ofDim(256, 256)
    }
  }
}

@JsonCodec
case class EmptyRequest (
  x: Int,
  y: Int,
  z: Int
) extends Request {
  val fill = scala.util.Random.nextInt(255)
  def toTile = IntArrayTile.ofDim(256, 256).map { x => fill }
}
