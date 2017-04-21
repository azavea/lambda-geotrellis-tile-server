package io.github.jisantuc.gtlambda

import geotrellis.raster.Tile
import geotrellis.raster.render.{ColorRamp, ColorMap}

package object tile {
  abstract class Request {
    val x: Int
    val y: Int
    val z: Int
    def toTile: Tile
  }
}
