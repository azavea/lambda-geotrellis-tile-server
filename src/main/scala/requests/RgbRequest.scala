package io.github.jisantuc.gtlambda.tile

import geotrellis.raster.{IntArrayTile, Tile}
import io.circe.generic.JsonCodec

import scala.beans.BeanProperty

@JsonCodec
case class RgbRequest (
  x: Int,
  y: Int,
  z: Int
) extends Request {
  def toTile = ???.asInstanceOf[Tile]
}

@JsonCodec
case class EmptyRequest (
  x: Int,
  y: Int,
  z: Int
) {
  def toTile = IntArrayTile.ofDim(256, 256)
}
