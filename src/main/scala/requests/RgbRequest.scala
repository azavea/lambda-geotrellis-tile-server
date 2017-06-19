package io.github.jisantuc.gtlambda.tile

import geotrellis.raster.{IntArrayTile, MultibandTile, Tile}
import geotrellis.raster.render.{ColorRamps, ColorMap}
import geotrellis.spark.{LayerId, SpatialKey}
import geotrellis.spark.io.ValueNotFoundError
import geotrellis.spark.io.s3.{S3ValueReader, S3AttributeStore}
import geotrellis.spark.io.avro._

import com.amazonaws.services.lambda.runtime.LambdaLogger
import io.circe.generic.JsonCodec

import java.net.URLDecoder
import spray.json._
import DefaultJsonProtocol._

@JsonCodec
case class RgbRequest (
  x: Int,
  y: Int,
  z: Int,
  bucket: String,
  prefix: String,
  layerName: String,
  vizType: String
) extends Request {
  implicit val spatialKeyFormat = jsonFormat2(SpatialKey.apply _)

  private def fetchValue[T: AvroRecordCodec](default: => T)(implicit logger: LambdaLogger) = {
    val p = URLDecoder.decode(prefix)
    val store = S3AttributeStore(bucket, p)
    val layerId = new LayerId(layerName, z)
    val reader = new S3ValueReader(store).reader[SpatialKey, T](layerId)
    try {
      reader.read(SpatialKey(x, y))
    } catch {
      case e: ValueNotFoundError =>
        logger.log(s"Empty tile: ${bucket} ${p} ${layerName} ${e}")
        default
    }
  }

  def toTile(implicit logger: LambdaLogger): Tile =
    fetchValue { IntArrayTile.ofDim(256, 256) }

  def toMultibandTile(implicit logger: LambdaLogger): MultibandTile =
    fetchValue {
      val t = toTile
      MultibandTile(t, t, t)
    }
}

@JsonCodec
case class EmptyRequest (
  x: Int,
  y: Int,
  z: Int
) extends Request {
  val fill = scala.util.Random.nextInt(255)
  def toTile(implicit logger: LambdaLogger) = IntArrayTile.ofDim(256, 256).map { x => fill }
  def toMultibandTile(implicit logger: LambdaLogger) = {
    val t = toTile
    MultibandTile(t, t, t)
  }
}
